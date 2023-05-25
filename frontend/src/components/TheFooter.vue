<template>
  <footer class="footer">
    <div class="container">
      <div class="footer-content">
        <div class="contact-info">
          <div class="logo">
            <a href="#">
              <img src="../assets/TravelMaker.png" alt="Logo" />
            </a>
          </div>
          <br />
          <p class="customer-service">고객센터 010 - 1234 - 5678</p>
          <p class="operation-hours">운영시간 매일 9:00 ~ 18:00</p>
          <br />
          <div class="social-logos">
            <img src="../assets/github.png" alt="GitHub" class="social-logo" />
            <img src="../assets/naver.png" alt="Naver" class="social-logo" />
            <img src="../assets/instagram.png" alt="Instagram" class="social-logo" />
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>

<style>
.footer {
  background-color: #f8f8f8;
  padding: 2rem 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.footer-content {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
}

.logo img {
  width: 200px;
  height: auto;
}

.contact-info {
  text-align: right;
}

.customer-service {
  font-weight: bold;
}

.operation-hours {
  margin-top: 0.5rem;
}

.social-logos {
  display: flex;
  align-items: right;
}

.social-logo {
  width: 35px;
  height: auto;
  margin-left: 1rem;
}
</style>
