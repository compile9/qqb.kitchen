const fs = require('fs');
const path = require('path');
const axios = require('axios');
const csv = require('csv-parser');
const { parse } = require('json2csv');

const csvFilePath = 'src/main/resources/data/recipes.csv';
const imagesDir = 'src/main/resources/recipes-images';
const imageColumn = 'image';

async function downloadImage(imageUrl, imagesDir) {
  try {
    const urlPath = new URL(imageUrl).pathname;
    const filename = path.basename(urlPath);
    // form a new local path
    const imagePath = path.join(imagesDir, filename);
    // download from ibb url
    const response = await (imageUrl, { responseType: 'arraybuffer', timeout: 30000 });
    // write image data into imageUrl
    fs.writeFileSync(imagePath, response.data);
    console.log(`Downloaded: ${imagePath}`);
    return imagePath;
  } catch (error) {
    console.error(`Error downloading ${imageUrl}: ${error.message}`);
    return null;
  }
}

async function processCsv() {
  const rows = [];
  fs.mkdirSync(imagesDir, { recursive: true });

  // read the CSV file as a stream
  fs.createReadStream(csvFilePath)
    .pipe(csv()) // for each row, parse the CSV into each object (key -> header, value -> cell)
    .on('data', (row) => rows.push(row)) // collect each parsed row (the object) into this array
    .on('end', async () => {
      console.log(`Read ${rows.length} rows from ${csvFilePath}`);

      const promises = rows.map(async (row) => {
        const imageUrl = row[imageColumn];
        const imagePath = await downloadImage(imageUrl, imagesDir);
        if (imagePath) {
          row[imageColumn] = imagePath;
        }
        return row;
      });

      // wait for all downloads to complete
      const updatedRows = await Promise.all(promises);

      // write the updated rows to a the same file
      const csvData = parse(updatedRows);
      fs.writeFileSync(csvFilePath, csvData);
      console.log(`CSV processed and saved to ${csvFilePath}`);
    });
}

processCsv();
