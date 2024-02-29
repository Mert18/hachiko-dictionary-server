const fs = require('fs');

// Path to the JSON file
const jsonFilePath = './words.json';

// Function to read JSON file
const readJSONFile = (filePath) => {
    try {
        const jsonString = fs.readFileSync(filePath);
        return JSON.parse(jsonString);
    } catch (error) {
        console.error('Error reading JSON file:', error);
        process.exit(1);
    }
};

const jsonData = readJSONFile(jsonFilePath);


db = db.getSiblingDB('hachiko-dictionary');

db.createCollection('words');
db.createCollection('accounts');
db.accounts.insertOne(
    {
        "_id" : ObjectId("65de34002a1ec24d2968b8bd"),
        "username" : "test",
        "password" : "$2a$10$xfyfSN4ym9pRYkc81L8BZuj9ouRvAQxv.1kSo8YqOZaAdJSMsZytW",
        "email" : "test@gmail.com",
        "role" : "USER",
        "confirmed" : true,
        "_class" : "com.m2t.hachikodictionary.model.Account"
    }
)



db.words.insertMany(jsonData);
