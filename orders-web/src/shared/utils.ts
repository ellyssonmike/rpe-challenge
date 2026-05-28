type CurrencyMode = 'divide' | 'multiply';
interface FormatCurrencyOptions {
  locale: Intl.LocalesArgument;
  currency: string;
  mode?: CurrencyMode;
  multiplier?: number;
}

export function formatDate(date: Date | string) {
  if (!date) return

  return Intl.DateTimeFormat("pt-BR", {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  }).format(new Date(date)).replace(',', '')
}

export function formatCurrency(value: string | number, options: FormatCurrencyOptions) {
  if (!value) return;

  const number = Number(value);
  const config: Intl.NumberFormatOptions = {
    style: 'currency',
    currency: options.currency,
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  };

  if (!options.mode || !options.multiplier) {
    return Intl.NumberFormat(options.locale, options).format(number)
  }

  return Intl.NumberFormat(options.locale, options).format(
    options.mode === 'divide'
      ? number/options.multiplier
      : number*options.multiplier
  )
}

export function formatCurrencyBR(value: string | number, mode?: CurrencyMode, multiplier?: number) {
  return formatCurrency(value, {
    locale: 'pt-BR',
    currency: 'BRL',
    multiplier,
    mode,
  });
}

export function formatCpf(value: string) {
  return value.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
}

export const maskCPF = (value: string) => {
  return value
    .replace(/\D/g, "")
    .replace(/(\d{3})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d{1,2})/, "$1-$2")
    .replace(/(-\d{2})\d+?$/, "$1");
};

export const maskUUID = (value: string) => {
  const cleaned = value.replace(/[^0-9a-f]/gi, '')
  if (cleaned.length === 0) return '';

  let formatted = '';
  for (let i = 0; i < cleaned.length; i++) {
    if (i === 8 || i === 12 || i === 16 || i === 20) {
      formatted += '-';
    }
    formatted += cleaned[i].toLocaleLowerCase();
  }

  return formatted.substring(0, 36);
};

export const maskCurrency = (value: string) => {
  const cleanValue = value.replace(/\D/g, "");
  if (cleanValue === "") return "";
  
  const floatValue = parseFloat(cleanValue) / 100;
  return new Intl.NumberFormat("pt-BR", {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  }).format(floatValue);
};