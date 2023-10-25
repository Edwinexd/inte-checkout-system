# Equivalance classes for EAN13

By [Edwin Sundberg](mailto:edwin@edthing.com)

## Classes

### Digits

| Id | Valid | Description                         | Comment                                                    |
|----|-------|-------------------------------------|------------------------------------------------------------|
| A1 | X     | Thirteen digits                     | Only EAN13 is supported                                    |
| A2 |       | Less than thirteen digits           |                                                            |
| A3 |       | More than thirteen digits           |                                                            |
| A4 | X     | Positive number                     |                                                            |
| A5 |       | Negative number                     |                                                            |
| A6 |       | First digit is zero                 | Standard is to interpret this as a UPC-A code, unsupported |
| A8 | X     | Last digit is correct check digit   |                                                            |
| A9 |       | Last digit is incorrect check digit |                                                            |

### Check digit

| Id | Valid | Description                                                                                        | Comment                                                               |
|----|-------|----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------|
| B1 | X     | Correct check digit                                                                                | According to https://boxshot.com/barcode/tutorials/ean-13-calculator/ |
| B2 |       | Incorrect check digit                                                                              |                                                                       |
| B3 | X     | Sum of digits in odd positions + 3 * sum of digits in even positions modulo 10 equals zero         |                                                                       |
| B4 | X     | Sum of digits in odd positions + 3 * sum of digits in even positions modulo 10 does not equal zero |                                                                       |

### Country code

| Id | Valid | Description           | Comment                                                                                         |
|----|-------|-----------------------|-------------------------------------------------------------------------------------------------|
| C1 | X     | Country code is valid | According to: https://www.barcodestalk.com/learn-about-barcodes/resources/barcode-country-codes |
| C2 |       | Country code invalid  |                                                                                                 |
| C3 |       | Internal country code | Reserved for internal use - 200-299                                                             |

## Test cases

| Id  | Input          | Output                                | Covered classes        | Comment                                      |
|-----|----------------|---------------------------------------|------------------------|----------------------------------------------|
| T1  | 1234567890128  | { country_code: 123, check_digit: 8 } | A1, A4, A8, B1, B3, C1 |                                              |
| T2  | 1              | IllegalArgumentException              | A2                     |                                              |
| T3  | 12345678901289 | IllegalArgumentException              | A3                     |                                              |
| T4  | -123456789012  | IllegalArgumentException              | A5                     | Digits with negative sign gets the length 13 |
| T5  | 0123456789012  | IllegalArgumentException              | A6                     |                                              |
| T6  | 1234567890127  | IllegalArgumentException              | A9                     |                                              |
| T7  | 1234567890120  | IllegalArgumentException              | B2                     | Overlapping with A9                          |
| T8  | 7194567890129  | IllegalArgumentException              | C2                     | 719 is an invalid country code               |
| T9  | 2004567890126  | IllegalArgumentException              | C3                     | 200 is an internal country code              |
| T10 | 1234567890050  | { country_code: 123, check_digit: 0 } | A1, A4, A8, B1, B4, C1 |                                              |

## Traceability matrix

| Id  | A1 | A2 | A3 | A4 | A5 | A6 | A8 | A9 | B1 | B2 | B3 | B4 | C1 | C2 | C3 |
|-----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|----|
| T1  | X  |    |    | X  |    |    | X  |    | X  |    | X  |    | X  |    |    |
| T2  |    | X  |    |    |    |    |    |    |    |    |    |    |    |    |    |
| T3  |    |    | X  |    |    |    |    |    |    |    |    |    |    |    |    |
| T4  |    |    |    |    | X  |    |    |    |    |    |    |    |    |    |    |
| T5  |    |    |    |    |    | X  |    |    |    |    |    |    |    |    |    |
| T6  |    |    |    |    |    |    |    | X  |    |    |    |    |    |    |    |
| T7  |    |    |    |    |    |    |    |    |    | X  |    |    |    |    |    |
| T8  |    |    |    |    |    |    |    |    |    |    |    |    |    | X  |    |
| T9  |    |    |    |    |    |    |    |    |    |    |    |    |    |    | X  |
| T10 | X  |    |    | X  |    |    | X  |    | X  |    |    | X  | X  |    |    |

