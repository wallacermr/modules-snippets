#!/bin/sh

# For execute this migrations script, install homebrew on: https://brew.sh/index_pt-br
# Install mongosh client with command: $ brew install mongosh
# If necessary run the command on this file: $ chmod -R 777 run-mongodb-on-container.sh

mongosh "mongodb://localhost:27017/order-producer" < order_producer/V1__create_structure.js