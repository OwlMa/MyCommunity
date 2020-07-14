# Tags table
- Tags table is designed by using the *BitSet* in java.
- We get the id of the article, and set the bit of id-th position to be 1. Through this way
we can set all articles with the same tag into one byte array, which reduce the memory storage of the table.
- If we have intensive data, then bit-map may be a good choice, but for this project, *BitSet*is enough.
- We need to convert the *BitSet* to byte array when update the schema and convert the byte array to the
*BitSet* when querying the data from database.