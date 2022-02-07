import { Router } from "express";

import * as wordController from "../controllers/word"

const router = Router();

router.get("/getwords", wordController.getWords);
router.get("/getword/random", wordController.getWordRandom);
router.get("/getword/:id", wordController.getWord);
router.post("/createword", wordController.createWord);
router.put("/updateword", wordController.updateWord);
router.delete("/deleteword", wordController.deleteWord);

export default router;
