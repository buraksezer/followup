# FollowUp

Spring, JPA ve Maven teknolojilerini kullanan basit bir Veteriner Hayvan Takip Uygulaması. Bir is gorusmesi icin odev 
olarak hazirlanmistir.

## Kurulum

FollowUp bir Java uygulamasidir. Gelistirme yapmak ve calistirmak icin JDK ve JRE sisteminizde kurulu olmalidir. Ubuntu icin:

`
sudo apt install default-jdk
`

Bu komut ile Java Development Kit (JDK) ve Java Runtime Environment (JRE) sisteminize kurulacaktir. 

FollowUp veritabani olarak MySQL kullanir. Eger sisteminizde kurulu degilse, Ubuntu icin asagidaki komut ile kurulabilir:

`
sudo apt-get install mysql-server
`

Kurulum yapildiktan sonra `src/main/resources/application.properties` dosyasi uygun sekilde degistirilmelidir:

```
spring.datasource.url = jdbc:mysql://localhost:3306/followup?useSSL=false
spring.datasource.username = root
spring.datasource.password = password
```

Eger sisteminizde MySQL zaten calisiyorsa yukaridaki degerleri uygun bir sekilde degistirebilirsiniz, eger yeni bir
kurulum yapmissaniz veritabanina erisim icin MySQL'e `root` haklariyla baglanarak:

`sudo mysql -u root -p`

MySQL'in komut satirina eristikten sonra `application.properties` dosyasinda belirtilen kullanici ve parola icin: 

`
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' IDENTIFIED BY 'password' WITH GRANT OPTION;
`

Komutunu calistirarak gerekli izinleri veriniz. Son olarak, uygulamanin veritabanini MySQL uzerinde olusturun:

`
CREATE DATABASE followup;
`

FollowUp bagimliliklarin yonetilmesi ve insa icin Apache Maven kullanir. Kok dizindeki `mvnw` isimli betik projeyi calisir
hale getirmek icin kullanilabilir:

```
./mvnw package
```

Bu komut `pom.xml` dosyasini okuyarak bagimliliklari indirir, testleri calistirir, projeyi derler ve paketler. 
Eger bu asamalar basari ile sonuclanmissa, program asagidaki komutla calistirilabilir:
 
`java -jar target/followup-{VERSION}.jar`

Alternatif bir yol olarak, tek komut ile: 

`
./mvnw spring-boot:run
`

FollowUp uygulamasi ontanimli olarak 8080 portunda calisir. Eger degistirmek isterseniz `src/main/resources/application.properties` dosyasi icine:

`
server.port = $PORT_NUMBER
`

Yukaridaki satiri ekleyerek baska bir portta calismasini saglayabilirsiniz. Alternatif bir yol olarak:

`
./mvnw spring-boot:run -Drun.jvmArguments='-Dserver.port=$PORT_NUMBER'
`

Sunucu basariyla calistirildiktan sonra tarayinin adres cubuguna [http://localhost:8080](http://localhost:8080) yazarak
FollowUp kullanmaya baslayabilirsiniz.
 

## Uygulamanin Ozellikleri

* Kullanicilar sistemi kullanmadan once kayit olmalidir,
* Her kullanici hasta kaydi yapabilir,
* Bir kullanici sinirsiz sayida hasta kaydi yapabilir,
* Uygulamaya giris yapildiktan sonra son yapilan kayitlar goruntulenir,
* Var olan hasta kayitlari arasinda arama yapilabilir,
* Kayitlar silinebilir ve guncellenebilir,
* Kullanicilari kendi kayitlarini guncelleyebilir ve silebilir,
* Yonetici haklarina sahip kullanici tum kayitlari guncelleyebilir ve silebilir,
* Kullanicilar kendi hesaplarini goruntuleyebilir ve duzenleyebilir,
* Yonetici haklarina sahip kullanici herkesin hesabini duzenleyebilir,
* Mevcut hasta kayitlari arasinda *isim* alani uzerinden arama yapilabilir,
* Mevcut kullanicilar arasinda *isim* ve *soyisim* alanlari uzerinden arama yapilabilir.

Uygulama OpenJDK 11 ve MySQL kullanilarak Ubuntu 18.10 uzerinde gelistirilmistir.

## Yonetici haklarina sahip kullanici olusturulmasi

Once MySQL'e baglanilir:

`
mysql -u root -p
`

*Not:* Kurulum adiminda kullandiginiz kullanici adi ve parolayi kullanmaniz gerekir. 

Asagidaki SQL sorgusu calistirilir:

`
UPDATE role r JOIN users_roles ur ON r.id = ur.role_id JOIN user u ON u.id = ur.user_id SET r.name='ROLE_ADMIN' where u.email='myemail@address.com'';
`

Sisteme kayit olurken kullandiginiz eposta adresini kullanmayi unutmayin! 

