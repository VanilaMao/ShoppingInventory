FROM node:13.12.0-alpine AS builder
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY app/package.json .
COPY app/package-lock.json .
RUN npm install --silent
RUN npm install react-scripts@3.4.1 -g --silent
COPY app/ .
RUN npm run build

FROM node:13.12.0-alpine
RUN npm install -g serve
WORKDIR /app
COPY --from=builder /app/build .
EXPOSE 80
CMD ["serve","-p","80","-s","."]