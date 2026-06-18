<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Pagamento</title>
  <style>
    *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

    body {
      font-family: system-ui, -apple-system, sans-serif;
      background: #f5f5f3;
      min-height: 100vh;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 2rem 1rem;
      color: #1a1a18;
    }

    .card {
      background: #fff;
      border-radius: 16px;
      border: 0.5px solid rgba(0,0,0,0.12);
      padding: 2rem;
      width: 100%;
      max-width: 480px;
    }

    .card-title {
      font-size: 18px;
      font-weight: 500;
      margin-bottom: 1.5rem;
    }

    .section-title {
      font-size: 12px;
      font-weight: 500;
      color: #888;
      text-transform: uppercase;
      letter-spacing: 0.06em;
      margin-bottom: 12px;
    }

    .form-section { margin-bottom: 1.5rem; }

    .field-group { display: flex; flex-direction: column; gap: 10px; }

    .field-row { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; }

    .field { display: flex; flex-direction: column; gap: 5px; }

    label { font-size: 13px; color: #555; }

    input, select {
      width: 100%;
      padding: 8px 12px;
      font-size: 14px;
      border: 0.5px solid rgba(0,0,0,0.18);
      border-radius: 8px;
      background: #fff;
      color: #1a1a18;
      outline: none;
      transition: border-color 0.15s, box-shadow 0.15s;
      height: 36px;
    }

    input:focus, select:focus {
      border-color: rgba(0,0,0,0.4);
      box-shadow: 0 0 0 3px rgba(0,0,0,0.06);
    }

    input::placeholder { color: #bbb; }

    .input-icon-wrap { position: relative; }
    .input-icon-wrap input { padding-right: 36px; }
    .input-icon-wrap .ico {
      position: absolute;
      right: 10px;
      top: 50%;
      transform: translateY(-50%);
      color: #bbb;
      font-size: 15px;
      pointer-events: none;
    }

    .card-badges { display: flex; gap: 6px; margin-bottom: 10px; }
    .badge {
      font-size: 11px;
      font-weight: 500;
      padding: 3px 8px;
      border-radius: 4px;
      background: #f5f5f3;
      color: #555;
      border: 0.5px solid rgba(0,0,0,0.12);
    }

    hr {
      border: none;
      border-top: 0.5px solid rgba(0,0,0,0.1);
      margin: 1.5rem 0;
    }

    .pay-btn {
      width: 100%;
      padding: 11px;
      background: #1a1a18;
      color: #fff;
      border: none;
      border-radius: 8px;
      font-size: 15px;
      font-weight: 500;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      margin-top: 1.5rem;
      transition: opacity 0.15s;
    }

    .pay-btn:hover { opacity: 0.82; }
    .pay-btn:active { transform: scale(0.98); }

    .secure-note {
      display: flex;
      align-items: center;
      gap: 6px;
      justify-content: center;
      margin-top: 10px;
      font-size: 12px;
      color: #aaa;
    }

    @media (max-width: 480px) {
      .field-row { grid-template-columns: 1fr; }
    }
  </style>
</head>
<body>

<form class="card" method="post" action="<%= request.getContextPath() %>/checkout">
  <h1 class="card-title">Completa il pagamento</h1>

  <div class="form-section">
    <p class="section-title">Indirizzo di fatturazione</p>
    <div class="field-group">
      <div class="field-row">
        <div class="field">
          <label for="nome">Nome</label>
          <input type="text" id="nome" name="nome" placeholder="Mario" autocomplete="given-name" required>
        </div>
        <div class="field">
          <label for="cognome">Cognome</label>
          <input type="text" id="cognome" name="cognome" placeholder="Rossi" autocomplete="family-name" required>
        </div>
      </div>
      <div class="field">
        <label for="indirizzo">Indirizzo</label>
        <input type="text" id="indirizzo" name="indirizzo" placeholder="Via Roma 12" autocomplete="street-address" required>
      </div>
      <div class="field-row">
        <div class="field">
          <label for="citta">Città</label>
          <input type="text" id="citta" name="citta" placeholder="Milano" autocomplete="address-level2" required>
        </div>
        <div class="field">
          <label for="cap">CAP</label>
          <input type="text" id="cap" name="cap" placeholder="20121" maxlength="5" autocomplete="postal-code" inputmode="numeric" required>
        </div>
      </div>
      <div class="field">
        <label for="paese">Paese</label>
        <select id="paese" name="paese" autocomplete="country">
          <option value="IT" selected>Italia</option>
          <option value="DE">Germania</option>
          <option value="FR">Francia</option>
          <option value="ES">Spagna</option>
          <option value="GB">Regno Unito</option>
          <option value="US">Stati Uniti</option>
        </select>
      </div>
    </div>
  </div>

  <hr>

  <div class="form-section">
    <p class="section-title">Dati della carta</p>
    <div class="card-badges">
      <span class="badge">VISA</span>
      <span class="badge">Mastercard</span>
      <span class="badge">Amex</span>
    </div>
    <div class="field-group">
      <div class="field">
        <label for="titolare">Titolare della carta</label>
        <input type="text" id="titolare" name="titolare" placeholder="Mario Rossi" autocomplete="cc-name" required>
      </div>
      <div class="field">
        <label for="numero">Numero carta</label>
        <div class="input-icon-wrap">
          <input type="text" id="numero" name="numero" placeholder="1234 5678 9012 3456" maxlength="19" autocomplete="cc-number" inputmode="numeric" required>
          <span class="ico">💳</span>
        </div>
      </div>
      <div class="field-row">
        <div class="field">
          <label for="scadenza">Scadenza</label>
          <input type="text" id="scadenza" name="scadenza" placeholder="MM/AA" maxlength="5" autocomplete="cc-exp" inputmode="numeric" required>
        </div>
        <div class="field">
          <label for="cvv">CVV</label>
          <div class="input-icon-wrap">
            <input type="password" id="cvv" name="cvv" placeholder="123" maxlength="4" autocomplete="cc-csc" inputmode="numeric" required>
            <span class="ico">🔒</span>
          </div>
        </div>
      </div>
    </div>
  </div>

  <button type="submit" class="pay-btn">🔒 Paga ora</button>
  <p class="secure-note">🛡️ Pagamento sicuro e crittografato</p>

</form>

<script>
  document.getElementById('numero').addEventListener('input', function() {
    let v = this.value.replace(/\D/g, '').slice(0, 16);
    this.value = v.replace(/(.{4})/g, '$1 ').trim();
  });

  document.getElementById('scadenza').addEventListener('input', function() {
    let v = this.value.replace(/\D/g, '').slice(0, 4);
    if (v.length > 2) v = v.slice(0,2) + '/' + v.slice(2);
    this.value = v;
  });

  document.getElementById('cap').addEventListener('input', function() {
    this.value = this.value.replace(/\D/g, '').slice(0, 5);
  });

  document.getElementById('cvv').addEventListener('input', function() {
    this.value = this.value.replace(/\D/g, '').slice(0, 4);
  });
</script>

</body>
</html>