countNums = function countNums(){
    // https://docs.mongodb.com/manual/tutorial/write-scripts-for-the-mongo-shell/
    
    cursor = db.phones.aggregate([{$group : { _id :  "$components.prefix", number_phones : {$sum : 1}}}]);
    while ( cursor.hasNext() ) {
        printjson( cursor.next() );
    }
}