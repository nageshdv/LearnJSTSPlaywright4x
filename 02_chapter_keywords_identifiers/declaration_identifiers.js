//"use strict";// --> blocks the user using a variable name without declaration


let students = 10;
console.log("Student = ",`${students}`);
students =12;
console.log("Student = ",`${students}`);

const teachers = 10;
//teachers = 15;//--> not possible to reassign
console.log("teachers = ",`${teachers}`);

var principal = 1;
console.log("principal = ",`${principal}`);
principal=2;
console.log("principal = ",`${principal}`);


for(i=0; i<3; i++){        
    console.log(i);
      //let j=i+1; //--> let cannot be used outside scope of the block and it will throw reference error 
      var j=i+1;
}

console.log("value of i out of the block ",`${i}`);

console.log("value of j out of the block ",`${j}`);


let country; //-> if let is unassigned then it will return undefined
//const state; // -> will throw error immediately - need to assign value with the declaration 
const state = "Delhi"
var city;
//pin; //-> pin is not defined - reference error
pin=560085;

console.log(`${country}`, `${state}`, `${city}`, `${pin}` );

if (principal === 2 ){
    console.log(principal);
    var one =1;
    const two =2;                    //const/let cant be accessed outside the block
    let three =3;
    console.log(`${one}`, `${two}`, `${three}`);
}


//console.log(`${one}`, `${two}`, `${three}`);