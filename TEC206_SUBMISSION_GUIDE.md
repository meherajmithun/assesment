# TEC206 Assessment 2 Submission Guide

## What to Submit

The assessment asks for the Python program in `.py` format.

Submit this main file:

- `TEC206_Assessment_2/codebase/bookstore_inventory.py`

If MyKBS allows more than one Python file, also submit:

- `TEC206_Assessment_2/codebase/test_bookstore_inventory.py`

The test file shows that the classes and validation were tested. Do not submit
the ZIP instead of the Python file unless your lecturer asks for a ZIP.

## Check the Program

Open Terminal and run:

```bash
cd /Users/meheraj/noyon/TEC206_Assessment_2/codebase
python3 bookstore_inventory.py
```

Check that you can add, display, and update books, and then exit the program.

Run the tests with:

```bash
python3 -m unittest -v
```

All five tests should pass.

## Upload to MyKBS

1. Open the TEC206 Assessment 2 submission page in MyKBS.
2. Upload `bookstore_inventory.py`.
3. Upload `test_bookstore_inventory.py` if multiple files are allowed.
4. Add the required AI disclosure and prompt/response appendix.
5. Save the upload and press the final submission button.
6. Reopen the submission page and confirm the correct files are attached.

## Final Checklist

- [ ] The main file has a `.py` extension.
- [ ] The program opens without an error.
- [ ] The menu continues until Exit is selected.
- [ ] Fiction and non-fiction books work correctly.
- [ ] Price and quantity validation works.
- [ ] The correct final files are attached in MyKBS.

