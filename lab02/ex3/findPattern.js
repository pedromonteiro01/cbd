findPattern = function findPattern(){
    var counter = 0;
    var pattern = "007";

    cursor = db.phones.find(); 
    while ( cursor.hasNext() ) { 
        var phone = cursor.next();
        var num = phone._id.toString().substring(3,12);
        
        if (num.includes(pattern)){
            counter++;
            print(num);
        }
    }
    print("Number of phone numbers that contains '007': ",counter);
}
