# Kois
Mehmet KARAKAYA

Merhaba bugün ki yazımda KOİS'den ve nasıl kullanılacağından bahsedicem. Öncelikle KOİS'in ne olduğundan biraz bahsedeğim.

# KOİS Nedir ?

Ortam iklimlendirmesi üzerine geliştirmiş olduğum proje, kullanıcının istediği ortam koşullarını stabil tutabilmek 
için bulunduğu ortam değerlerini ölçerek(hava kalitesi, nem, sıcaklık, basınç, yangın durumu) gerekli durumda ortam
koşullarına müdahale edebilen(fan, ısı, alarm), gerçek zamanlı olarak çalışan projemdir. Aynı zamanda ölçülen sensör
verileri serial portu üzerinden bilgisayara aktarılarak veritabanında kayıt altına alınmaktadır. Kayıt altına alınan
veriler tasarlamış olduğum görselde anlık veya geçmişe dönük olarak görselleştirilmektedir. Bu amaçlar doğrultusunda 
Java ve SQL kütüphanelerinden faydalanılarak Java, SQL, C ve C++ yazılım dilleriyle geliştirme
yapılmıştır.

# Proje kapsamında kullanılan araçlar

* Arduino Mega 2560 R3
* LCD Shield
* BME280 Sıcaklık Nemi Barometrik Basınç Sensörü
* MQ-135 Hava Kalitesi Ölçüm Sensörü
* Alev Sensörü
* Buzzer
* Led (Kırmızı, Sarı, Yeşil)

# Bağlantı Pinleri

* Kırmızı Led             : 22
* Yeşil Led               : 24
* Sarı Led                : 26
* Hava Kalitesi Sensörü   : A8
* Yangın Sensörü          : 28
* Buzzer                  : 53
* Klima Soğuk             : 50  (Temsili olarak led bağlanmıştır)
* Klima Sıcak             : 51  (Temsili olarak led bağlanmıştır)

# GUI ve Veri Tabanı

Netbeans yazılım geliştirme ortamı üzerinde KOİS projesi için tasarladığım GUI bulumaktadır. Serial port üzerinden 
aktarılan sensör verileri veritabanına anlık olarak kaydedilmektedir.

