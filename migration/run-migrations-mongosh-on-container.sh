#!/bin/sh

# For execute this migrations script, install homebrew on: https://brew.sh/index_pt-br
# Install mongosh client with command: $ brew install mongosh
# If necessary run the command on this file: $ chmod -R 777 run-migrations-mongosh-on-container.sh

mongosh "mongodb://mongo1.dev:27017/mongodb-pagination" < mongodb-pagination/V1__create_initial_structure.js
mongosh "mongodb://mongo1.dev:27017/mongodb-pagination" < mongodb-pagination/V2__create_collection_estado.js