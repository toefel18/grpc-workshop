# Exercise 1 - protocol buffers

For info on types etc: [Protobuf 3 docs](https://developers.google.com/protocol-buffers/docs/proto3)

1. Create a .proto file in src/main/proto  

1. The file should use proto3 syntax and have a package, for example:

    ```proto
    syntax = "proto3";
    package nl.toefel.exercise1;
    ```
    
1. Create a message named `HotelBooking`, it should have the fields:
  
    * Arrival date
    * Departure date
    * Last check-in time 
    * Price (use float)
    
1. Run `./gradlew generateProto`

1. Create a java class `nl.toefel.exercise1.Exercise1` with a main function.

1. Create an instance of HotelBooking using the builder, set all fields.

1. Write the instance to a file "hotel-booking-1.binary" using the writeTo method on the message.

1. The file will contain mostly binary data

1. Read the HotelBooking from disk (using parseFrom)' and print it to the console 

## Testing backward compatibility 

1. Keep the file "hotel-booking-1.binary" around and comment the write part out   
   to avoid overwriting it.

1. Add a message `Guest` with fields, name, age and gender. Gender must be an enum.

1. Modify HotelBooking to include an list of guests.

1. Run `./gradlew generateProto`

1. Now read the file "hotel-booking-1.binary" from disk again using the new message 
   definition and print it, is the output different? 
   
1. Now add a guest in your new format and save it to "hotel-booking-2.binary".

1. Revert the .proto file back to only the HotelBooking without any Guests in the message
   definition. 
   
1. Run `./gradlew generateProto`

1. Now read the file "hotel-booking-2.binary" from disk again using the old message 
   definition and print it. The output should look different now.

1. Now print the hotel booking as JSON using JsonFormat
