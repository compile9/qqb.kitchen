#!/usr/bin/env python3
import pandas as pd
import requests
import os
import argparse
from urllib.parse import urlparse
import time

def process_csv(input_csv, output_csv, images_dir, image_column):
    os.makedirs(images_dir, exist_ok=True)
    df = pd.read_csv(input_csv)

    # iterate each row:
    for index, row in df.iterrows():
        image_url = row[image_column]
        try:
            # download the image
            print(f"Downloading image {index+1}/{len(df)}: {image_url}")
            response = requests.get(image_url, timeout=30)
            response.raise_for_status()
            # extract filename from the original url
            url_path = urlparse(image_url).path # eg: https://i.ibb.co/NrfMY0Y/mini-bite-beef-bao.webp
            filename = os.path.basename(url_path)  # eg: mini-bite-beef-bao.webp
            # form a new relative path
            image_path = os.path.join(images_dir, filename) # recipes-images/mini-bite-beef-bao.webp
            # store the image info in the new relative path location:
            with open(image_path, 'wb') as f:
                # put the downloaded image data into the file
                f.write(response.content)
            # update the CSV with the new path
            df.at[index, image_column] = image_path
            print(f"Processed image: {image_path}")
            # add delay between downloads
            time.sleep(0.5)
        except Exception as e:
            print(f"Error processing image at row {index+1}: {str(e)}")

    # save the CSV
    df.to_csv(output_csv, index=False)
    print(f"CSV processed and saved to {output_csv}")

def main():
    parser = argparse.ArgumentParser(description='Process CSV to download images')
    parser.add_argument('--input-csv', required=True, help='Path to input CSV')
    parser.add_argument('--output-csv', required=True, help='Path to output CSV')
    parser.add_argument('--images-dir', required=True, help='Directory to save images')
    parser.add_argument('--image-column', required=True, help='Column with image URLs')

    args = parser.parse_args()

    process_csv(args.input_csv, args.output_csv, args.images_dir, args.image_column)

if __name__ == "__main__":
    main()
