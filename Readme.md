## !!-- DiKkAt -- BuSeNiN dIkKaTıNe --!!  oKu
--> thread'e sout atıldı silince calismiyor(followservice)      

--> elastic de data cekmek icin kullanilan feign ilk calistirmada ise yaramıyor
bu yüzden önce elastic calistirilip sonra postmicro calistirilmali
ki post istek atinca elastic alsin      



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

## yapilacaklar.
* elastic search de db ye kayıtlı veri var ise kaydetme yok ise kaydet işlemi.
* change password bağlanacak.
* a
* profile.html
* people.html
* commentread ler için commenttoposta tarih ve saat eklenmeli
* comment tuşu çok saçma duruyor comment alanı comment tuşuna basılarak açılmalı



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


