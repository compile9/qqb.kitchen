# Build Stage
FROM node:20.18-alpine AS build
WORKDIR /app

COPY package.json .
COPY package-lock.json .

RUN npm ci

COPY webpack.config.ts .
COPY tsconfig.json .

COPY src/components ./src/components
COPY src/*.tsx ./src/
COPY src/*.css ./src/
COPY public/ ./public

RUN npm run build-frontend

# Production Stage
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
