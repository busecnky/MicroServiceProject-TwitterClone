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

--- DOCKER IMAGE OLUSTURMA ---
### YAPILACAKLAR:
* application.yml ---> ZIPKIN_URL düzenlencek
* microservice git ymllarında ----> url şifre her şeye bakılacak.


#### Image oluştururken dikkat edilecekler
* microservicelere clean build build dependants yapmayı unutmuyoruz.
* microservicelerin içindeki build içindeki LİBS klasörünün copy pathini alıyoruz.
* sonra alttakiler gibi image versiyonu neyse ordan devam ediyoruz ilk sefer değilse.

* docker build  --build-arg JAR_FILE=ConfigServerGit/build/libs/ConfigServerGit-v.0.1.jar --platform linux/amd64 -t busecnky/pawerconfigservergit:003 .
* docker build --build-arg JAR_FILE=AuthMicroService/build/libs/AuthMicroService-v.0.1.jar --platform linux/amd64 -t busecnky/pawerauthmicroservice:001 .
* docker build --build-arg JAR_FILE=UserMicroService/build/libs/UserMicroService-v.0.1.jar --platform linux/amd64 -t busecnky/pawerusermicroservice:001 .
* docker build --build-arg JAR_FILE=PostMicroService/build/libs/PostMicroService-v.0.1.jar --platform linux/amd64 -t busecnky/pawerpostmicroservice:001 .


