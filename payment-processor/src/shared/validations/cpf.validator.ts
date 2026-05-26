export const validateCPF = (cpf: string): boolean => {
  const cleanCpf = cpf.replace(/\D/g, '');

  if (cleanCpf.length !== 11) return false;
  if (/^(\d)\1+$/.test(cleanCpf)) return false;

  const digits = cleanCpf.split('').map(Number);

  const calculateCheckDigit = (base: number[], factor: number) => {
    const sum = base.reduce((acc, num, idx) => {
      return acc + num * (factor - idx);
    }, 0);

    const rest = (sum * 10) % 11;
    return rest === 10 || rest === 11 ? 0 : rest;
  };

  const firstCheck = calculateCheckDigit(digits.slice(0, 9), 10);
  if (firstCheck !== digits[9]) return false;

  const secondCheck = calculateCheckDigit(digits.slice(0, 10), 11);
  if (secondCheck !== digits[10]) return false;

  return true;
};
