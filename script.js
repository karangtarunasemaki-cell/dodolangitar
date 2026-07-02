// Function to handle Buy Button click
function beliGitar(namaGitar, hargaGitar) {
    // Nomor WhatsApp penjual (Contoh dummy)
    const nomorWA = "6281234567890";
    
    // Format pesan
    const pesan = `Halo DODOLAN GITAR, saya ingin memesan produk berikut:\n\n` +
                  `Nama Produk: ${namaGitar}\n` +
                  `Harga: ${hargaGitar}\n\n` +
                  `Apakah stok masih tersedia?`;
    
    // Encode URL agar format spasi dan enter terbaca di URL WhatsApp
    const encodedPesan = encodeURIComponent(pesan);
    
    // URL WhatsApp API
    const waUrl = `https://wa.me/${nomorWA}?text=${encodedPesan}`;
    
    // Membuka tab baru ke WhatsApp
    if(confirm(`Anda akan diarahkan ke WhatsApp untuk membeli ${namaGitar}. Lanjutkan?`)){
        window.open(waUrl, '_blank');
    }
}

// Simple animation when scrolling (optional aesthetic addition)
document.addEventListener("DOMContentLoaded", () => {
    const cards = document.querySelectorAll('.product-card, .info-card');
    
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if(entry.isIntersecting) {
                entry.target.style.opacity = 1;
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, { threshold: 0.1 });

    cards.forEach(card => {
        card.style.opacity = 0;
        card.style.transform = 'translateY(20px)';
        card.style.transition = 'all 0.5s ease-out';
        observer.observe(card);
    });
});
