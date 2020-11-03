FROM node:14.1-alpine AS builder

WORKDIR /opt/web
COPY package.json package-lock.json ./
RUN npm install

ENV PATH="./node_modules/.bin:$PATH"

COPY . ./
RUN ng build --prod

FROM nginx:1.17-alpine
COPY nginx.config /etc/nginx/conf.d/default.conf
COPY --from=builder /opt/web/dist/angular-oauth2 /usr/share/nginx/html
