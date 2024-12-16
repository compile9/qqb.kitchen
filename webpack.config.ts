import path from 'path';
import { Configuration, DefinePlugin } from 'webpack';
import 'webpack-dev-server';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import dotenv from 'dotenv';

const env = dotenv.config().parsed;
const host = process.env.HOST;
const port = process.env.PORT;

const config: Configuration = {
  mode: 'development',
  entry: './src/index.tsx',
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'bundle.js',
    clean: true
  },
  module: {
    rules: [
      { test: /\.tsx?$/, use: ['ts-loader'], exclude: /node_modules/ },
      { test: /\.css$/, use:['style-loader', 'css-loader'], exclude: /node_modules/}
    ],
  },
  devServer: {
    static: {
        directory: path.join(__dirname, 'public'),
    },
    compress: true,
    port: 3000,
    historyApiFallback: true,
    proxy: [{
        context: ['/api'],
        target: `http://${host}:${port}`,
        changeOrigin: true,
        secure: false
    }],
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js'],
    alias: {events: 'events'}
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './public/index.html',
    })
  ]
};

export default config;
