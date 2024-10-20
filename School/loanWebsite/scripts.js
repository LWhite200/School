function calculateDebt() {
    document.getElementById('formSection').style.display = 'none';
    document.getElementById('resultsSection').style.display = 'block';

    const bYear1 = parseInt(document.getElementById('borrowingYear1').value);
    const loanAmt1 = parseFloat(document.getElementById('loanAmt1').value);
    const loanAmt2 = parseFloat(document.getElementById('loanAmt2').value);
    const loanAmt3 = parseFloat(document.getElementById('loanAmt3').value);
    const loanAmt4 = parseFloat(document.getElementById('loanAmt4').value);
    let bYear2 = bYear1 + 1;
    let bYear3 = bYear1 + 2;
    let bYear4 = bYear1 + 3;


    const interestRate = 0.05; // 5% interest
    const yearsBetween1and4 = bYear4 - bYear1;
    let totalDebt = loanAmt1 * Math.pow(1 + interestRate, yearsBetween1and4) +
    loanAmt2 * Math.pow(1 + interestRate, yearsBetween1and4 - 1) +
    loanAmt3 * Math.pow(1 + interestRate, yearsBetween1and4 - 2) +
    loanAmt4; // Last loan has no interest yet
    const repaymentYears = 10;
    const amortization = (totalDebt * interestRate) / (1 - Math.pow(1 + interestRate, -repaymentYears));
    document.getElementById('totalDebt').textContent = `Total Debt: $${totalDebt.toFixed(2)}`;
    document.getElementById('amortization').textContent = `Annual Amortization Payments: $${amortization.toFixed(2)}`;
}

function updateYears() {
    let bYear1 = parseInt(document.getElementById('borrowingYear1').value);
    if (!isNaN(bYear1) && bYear1 >= 0) {
        document.getElementById('borrowingYear2').value = bYear1 + 1;
        document.getElementById('borrowingYear3').value = bYear1 + 2;
        document.getElementById('borrowingYear4').value = bYear1 + 3;
    } else {
        document.getElementById('borrowingYear2').value = '';
        document.getElementById('borrowingYear3').value = '';
        document.getElementById('borrowingYear4').value = '';
    }
}


calculateDebt();
updateYears();