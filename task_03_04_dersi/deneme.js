
console.log('merhabaaaa')
console.error('patlamak üzere')
console.warn("patladı")
console.info("bilgi verildi")

const sabit = 111 
console.log(sabit)

let degisken = 222
console.log(degisken)

//sabit = 333
console.log(sabit)

degisken = 444
console.log(degisken)

let mft =  "MFT"

console.log("------"+`${mft}`)

// )karakter sayısını console a yazdırınız [16:14] Onur Alp Aydın (BilgeAdam Boost) 2)ilk 11 karakteri consola yazdırınız
// 3)11. karakterden sonrasını ekrana yazdırınız
// 4)son 4 karakteri ekrana yazdırınız.
// 5)5. karakterden sonra gelen 10 karakteri ekrana yazdırınız
// 6)sondan 15 karakter hariç tüm metni yazdırınız.
// 7)Metnimizin ilk 7 harfini alınız
const str = "Yatağımın karşısında bir pencere var. Odanın duvarları bomboş. Nasıl yaşadım on yıl bu evde? Bir gün duvara bir resim asmak gelmedi mi içimden? Ben ne yaptım? Kimse de uyarmadı beni. İşte sonunda anlamsız biri oldum. İşte sonum geldi. Kötü bir resim asarım korkusuyla hiç resim asmadım; kötü yaşarım korkusuyla hiç yaşamadım -Oğuz Atay , Tutunamayanlar"

const zeroto11=str.substring(0,10);
console.log("ilk 11..."+zeroto11);

const srclength= str.length
console.log("uzunluk..."+srclength);

const after11= str.substring(10)
console.log("11 karakter sonrası..."+after11);


const after5= str.substring(4,14)
console.log("5sonrası 10 karakter..."+after5)

const beforelast15= str.substring(0,str.length-14)
console.log ("son 15 hariç..."+beforelast15)




for(let i = str.length ; i > str.length-5 ; i--){
    let chars= str[i];
    console.log(chars)
}

console.log("*************************************************************************")


let s1 = 5;
let s2 = 2;
let s3 = 3;
let s4 = 4;

if (s1>s2) {
    console.log(s1);
} else {
    console.log(s2);
}

const higher = s1 > s2 ? s1 : s2;
console.log(higher);

let enBuyukSayi;
if(s1>s2 && s3>s4 && s1>s3) enBuyukSayi = s1;
else if(s2>s1 && s2>s3 && s2>s4) enBuyukSayi = s2;
else if(s3>s1 && s3>s2 && s3>s4) enBuyukSayi = s3;
else enBuyukSayi = s4;
//kullanıcıya haftanın gününü soralım. cevap olarak verilen günün sayı karşılığını gösterelim 
//ör salı=> haftanın 2. günüdür


//kullanıcıdan öğrenmek istediği ay numarasını alalım. Ay numarasına göre öğrenmek istediği ayı consola yazdıralım

//ör 2 => şubat ayıdır.
const gün =prompt(" gün gir")
switch (gün) {
    case "p.tesi":
      console.log("1");
      break;
    case "salı":
      console.log("2");
      break;
    case "crs":
      console.log("3");
      break;
    case "pers":
      console.log("4");
      break;
    case "cuma":
      console.log("5");
      break;
      case "c.tesi":
      console.log("6");
      break;
      case "pazar":
      console.log("7");
      break;
    default:
      console.log("yanlis girdin");
      break;
  }


  const ay = prompt ("istediginiz ay 'ın sırasını giriniz")



switch (Number(ay)) {
    case 1:
      
      console.log("ocak");
      break;
    case 2:
      console.log("şubat");
      break;
    case 3:
      console.log("mart");
      break;
    case 4:
      console.log("nisan");
      break;
    case 5:
      console.log("mayıs");
      break;

    case 6:
      console.log("haziran");
      break;
    case 7:
      console.log("temmuz");
      break;
    case 8:
      console.log("ağustos");
      break;
    case 9:
      console.log("eylül");
      break;
    case 10:
      console.log("ekim");
      break;

    case 11:
      console.log("kasım");
      break;

    case 12:
        console.log("aralık");
        break;     
    
    default:
      console.log("böyle bir ay tarihi yok ");
      break;
  }