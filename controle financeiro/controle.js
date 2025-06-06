const chave_transacoes_ls = "transacoes";

const form = document.getElementById("form");

const descInput = document.getElementById("descricao");
const valueInput = document.getElementById("montante");
const sinalInput = document.getElementsByName("radSinal");

const titleSaldo = document.getElementById("balanco");

const receitaP = document.getElementById("din-positivo");
const despesaP = document.getElementById("din-negativo");

const transacoesUL = document.getElementById("transacoes");

let transacoesSalvas = null;

if (localStorage.getItem(chave_transacoes_ls) != "") {
  try {
    transacoesSalvas = JSON.parse(localStorage.getItem(chave_transacoes_ls));
  } catch (err) {
    console.log("Erro:", err);
  }
}

if (transacoesSalvas === null) {
  transacoesSalvas = [];
}

let transacaoID = transacoesSalvas.length;

form.addEventListener("submit", (event) => {
  // Não submete o formulário - Retira funcionalidade padrão do elemento
  event.preventDefault();

  const descTransacao = descInput.value.trim();
  let valueTransacao = valueInput.value.trim();

  if (descTransacao == "") {
    alert("[ERRO] O campo 'Descrição' não pode ser vazio!");
    descInput.focus();
    return;
  }

  if (valueTransacao == "") {
    alert("[ERRO!!] O campo 'Valor' não pode ser vazio!");
    valueTransacao.focus();
    return;
  }

  valueTransacao = Math.abs(valueTransacao);

  if (sinalInput[0].checked) {
    valueTransacao *= 1;
  } else if (sinalInput[1].checked) {
    valueTransacao *= -1;
  } else {
    alert("[ERRO] O campo de 'Entrada' deve ser escolhido!");
    return;
  }

  const transacao = {
    id: transacaoID,
    desc: descTransacao,
    valor: parseFloat(valueTransacao),
  };

  somaAoSaldo(transacao);
  somaReceitaDespesa(transacao);
  addTransacaoAoDOM(transacao);
  // Adicionando ao vetor de transações
  transacoesSalvas.push(transacao);
  transacaoID++;
  localStorage.setItem(chave_transacoes_ls, JSON.stringify(transacoesSalvas));
});

function somaAoSaldo(transacao) {
  let valorBalanco = titleSaldo.innerHTML.trim();
  valorBalanco = valorBalanco.replace("R$", "");
  valorBalanco = parseFloat(valorBalanco);
  valorBalanco += transacao.valor;
  titleSaldo.innerHTML = `R$${valorBalanco.toFixed(2)}`;
}

function somaReceitaDespesa(transacao) {
  const elemento = transacao.valor > 0 ? receitaP : despesaP;
  const substituir = transacao.valor > 0 ? "+ R$" : "- R$";
  let valor = elemento.innerHTML.replace(substituir, "");
  valor = parseFloat(valor);
  valor += Math.abs(transacao.valor);
  elemento.innerHTML = `${substituir}${valor.toFixed(2)}`;
}

function addTransacaoAoDOM(transacao) {
  const cssClass = transacao.valor > 0 ? "positivo" : "negativo";
  const currency = transacao.valor > 0 ? "+R$" : "-R$";
  const liElementStr = `${transacao.desc} <span>${currency} ${Math.abs(
    transacao.valor
  )}</span><button class="delete-btn" onclick="deletaTransacao(${
    transacao.id
  })">X</button>`;

  const liElement = document.createElement("li");
  liElement.innerHTML = liElementStr;
  liElement.classList.add(cssClass);
  transacoesUL.appendChild(liElement);
  descInput.value = "";
  valueInput.value = "";
  for (radioIndex in sinalInput) {
    sinalInput[radioIndex].checked = false;
  }
}

function deletaTransacao(id) {
  const transacaoIndex = transacoesSalvas.findIndex(
    (transacao) => transacao.id == id
  );
  transacoesSalvas.splice(transacaoIndex, 1);
  localStorage.setItem(chave_transacoes_ls, JSON.stringify(transacoesSalvas));
  carregarDados();
}

function carregarDados() {
  transacaoID = transacoesSalvas.length;
  transacoesUL.innerHTML = "";
  titleSaldo.innerHTML = "R$0.00";
  receitaP.innerHTML = "+ R$0.00";
  despesaP.innerHTML = "- R$0.00";

  for (transacao of transacoesSalvas) {
    somaAoSaldo(transacao);
    somaReceitaDespesa(transacao);
    addTransacaoAoDOM(transacao);
  }
}

carregarDados();

console.log(transacoesSalvas);
