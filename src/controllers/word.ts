import { RequestHandler, Request, Response } from "express";
import Word from "../db/models/word";
import { IWord } from "../types";
import connectDB from "../db/mongoose";

/**
 * Create a new word.
 * @route POST /api/dictionary/createword
 */

export const createWord: RequestHandler = async (req: Request, res: Response) => {
  try {
    const word = new Word(req.body);
    await word.save();
    res.status(201).send({ message: "Created Word", createdWord: word });
  } catch (error: any) {
    res.status(500).send(error.message);
  }
};


/**
 * Get all words.
 * @route GET /api/dictionary/getwords
 */
export const getWords: RequestHandler = async (req: Request, res: Response) => {
  try {
    const words = await Word.find({});
    res.json(words);
  } catch (error: any) {
    res.status(404).send(error.message);
  }
};

/**
 * Get one word.
 * @route GET /api/dictionary/getwords/:id
 */
export const getWord: RequestHandler = async (req: Request, res: Response) => {
  try {
    let id = req.params.id;
    const word = await Word.find({ title: id });
    res.json(word);
  } catch (error: any) {
    res.status(404).send(error.message);
  }
};

/**
 * Get one random word.
 * @route GET /api/dictionary/getword/random
 */
export const getWordRandom: RequestHandler = async (req: Request, res: Response) => {
  try {
    const word = await Word.aggregate([{ $sample: { size: 1 } }]);
    res.json(word);
  } catch (error: any) {
    res.status(404).send(error.message);
  }
};

/**
 * Delete the given word.
 * @route DELETE /api/dictionary/deleteword
 */
export const deleteWord: RequestHandler = async (req: Request, res: Response) => {
  try {
    await Word.findOneAndDelete({ title: req.body.title as string }).then(
      (result) => {
        res.status(200).send(result);
      }
    );
  } catch (error: any) {
    res.status(404).send({ message: "Cannot delete word" });
  }
};

/**
 * Update a word.
 * @route PUT /api/dictionary/updateword
 */
export const updateWord: RequestHandler = async (req: Request, res: Response) => {
  try {
    await Word.findOneAndUpdate(
      { title: req.body.title },
      {
        $set: {
          description: req.body.newDescription as String,
        },
      }
    ).then((result) => {
      console.log(result);
      res.status(201).send({ message: "Successfully updated!" });
    });
  } catch (error: any) {
    res.status(404).send({ message: "Cannot find word!" });
  }
};
