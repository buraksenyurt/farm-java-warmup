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
