# Farm Java Warmup

Kurumsal çözümlerde Java diliyle ilerlemeden önce ısınma turlarının atıldığı deneysel repo.

## Bölüm 1

POJO ve JavaBean kavramlarının temel farkları ve kullanım alanları. POJO *(Plain Old Java Object)*, hiçbir framework veya base sınıfa bağımlı olmayan sade Java nesnesidir. JavaBean is bir sözleşmedir *(contract)*. Her JavaBean bir POJO'dur ama tersi doğru değildir.

Örneklerde takip;

- **commit 164bf64919d4d4bb3090e1ee21d08961d23093e0 :** `CustomerBean` sınıfı kasıtlı olarak geçersiz bir iş nesnesi oluşturacak şekilde tasarlanmıştır.
  - default constructor ile oluşturulan müşteri nesnesi geçersizdir.
  - companyName alanı boş veya null olamaz.
  - customerId alanı belirli kurallara göre doğrulanmalıdır.
  - contactName ve country alanları da iş kurallarına göre doğrulanmalıdır.
- **commit c9c915f04588765570fb393b9b6c6400ced77a46 :** Constructor ile geçerli bir nesne üretme sorumluluğunu ele alan örneği içerir. `Address` sınıfına bakınız.
- **commit 893920eb7826f9cd18ea2037317a857ac9a0e6ac :** `Address` nesnesinin aynısı aslında bir **record** türü olarak da tasarlanabilir. `PostalAddress` koduna bakınız.
- **commit 00da95d9eb7590a684ba6f362f4bace7faaa3142 :** Parasal veriler double tanımlanmaz. `BigDecimal` tipini kullanmak gerekir. `Money` record bu yaklaşımı örnekler.
- **commit d07c9084285eb73f6b86b12a356070affc90b519 :** `Order` sınıfı rich entity olarak tasarlanmıştır. Neyi çözdüğünü görmek için `AnemicOrder` sınıfı ile kullanımı karşılaştırılmaktadır. Burada bazı soruları da tartışmak gerekir. Anemic örnekte getlines().add ile gönderilmiş bir siparişe satır eklenmesi mümkündür ama rich entity tasarımında bu bir state ihlalidir ve **IllegalStateException** ile sonuçlanır. Calculation ile bulunan toplam tutar hesabı Anemic örnekte farklı yerlerde farklı şekilde yazılabilirken rich entity modelin tek bir metot üzerinden hesaplanır. Ayrıca anemic model örneğinde kuruş farkı çıkar. Bir diğer handikap şudur. Anemic modelde boş sipariş onaylanabilirken rich entity düzeneğinde bu mümkün değildir.
