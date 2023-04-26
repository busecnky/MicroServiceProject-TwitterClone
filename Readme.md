## !!-- DiKkAt -- BuSeNiN dIkKaTıNe --!!  oKu
--> thread'e sout atıldı silince calismiyor(followservice)      

--> elastic de data cekmek icin kullanilan feign ilk calistirmada ise yaramıyor
bu yüzden önce elastic calistirilip sonra postmicro calistirilmali
ki post istek atinca elastic alsin      

--> ana sayfa ya thread atildi cünkü sayfa yüklenirken takip ettiklerimizin postlarini
görüntülüyoruz.bunun icinde rabbitten istek atmamiz lazim. rabbitte yavas kaldigi
icin post controller'da findallpage'e 1sn lik thread atildi ki o rabbit
istegi 1sn de alinsin.(postmicro controller)       

--> userda alinan serilizeiable hatasinin sebebi rabbitden 
istek attigimiz metodu redis ile Cacheable etmekmis. cach kaldirildi düzeldi.



## yapilanlar:
* post'a faw tuşu eklendi. kontrol edildi tam calisiyor.(furkan)
* my faw sayfası eklendi. kontrol edildi tam calisiyor.(furkan)
* like atma düzeltildi. kontrol edildi tam calisiyor.(furkan)
* isteklerin olduğu sayfa eklendi o sayfaya istek sayısı eklendi(buse)
* takip isteği cevaplama eklendi (buse)
* postmicro servisleri düzenlendi.
* usermicro servisleri düzenlendi.
* rabbit istekleri postmicro'da controller'a tasindi.
* dtolar düzenlendi.
* ana sayfada takip ettiklerimin postlarını gösterme eklendi.
* kesfet eklendi.
----
* redis eklendi.
* elastic eklendi.
* fetch istekleri düzenlendi tamamen dogru yerlere istekler atiliyor.
* sayfa gösterme icin postmicroya atilan fetch isteği elasticmicro'ya tasindi.
* index.html düzenlendi.
* diger html sayfalari düzenlendi.(favpost,mypost,discover)
* sayfalardaki bu sol taraftaki menü hepsine eklendi calisiyor.


## yarim kalanlar:
--> elastic bir sacma calisiyor suan findallpage fetch istegi elastic e atiliyor.
like ve fav tusları tamamen calisiyor ama degisiklik görmek icin sayfayı manuel sekilde kendimiz
yenilememiz gerekiyor. bi sacmaliyor elastic muhtemelen feign istekleri elastic'in hizina yetisemiyor.
## yapilacaklar.
* sol taraftaki cubukta hangi sayfadaysak o büyütülecek vs.
* profile.html
* people.html


---
* takip istekleri sayısı için fetch istekleri atılacak.(front)
* pagination a bi bakılacak (front)
---




    # style.css' de yaptigim tüm degisiklikler. renklerden dolayı kör oluyordum


    .md-textarea{
    height: 100px;
    resize: none;
    padding: 20px;
    /*background-color: var(--color5);*/
    /*color: var(--color1);*/
    margin: 20px;
    font-size: 14px;
    border-radius: 25px;                -->0
    color: white;                       -->1
    background-color: #002254;          -->2
    outline: none; }
    
    
    .md-textarea::placeholder{
    color: var(--color1);
    color: white;                       -->3
    font-family: 'Poppins', sans-serif;}
    

    .pawbutton:hover,
    .pawbutton:focus {
    box-shadow: 0 0.5em 0.5em -0.4em var(--color3);
    transform: translateY(-0.25em);
    background-color: #78c059;          -->4
    }

    .post__body{
    padding: 20px;
    border-radius: 10px;
    background-color: gray;             -->5
    box-shadow: -10px -10px 15px rgba(255, 255, 255, 0.389),
    10px 10px 15px rgba(70, 70, 70, 0.15),
    inset -10px -10px 15px rgba(255, 255, 255, 0.3),
    inset 10px 10px 15px rgba(70, 70, 70, 0.15);
    width: 500px;
    }


