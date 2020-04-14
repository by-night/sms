let dealTotal = (value) => {
  let str = value.toString();
  // 是否存在小数
  let len = str.lastIndexOf(".");
  // num: 不存在小数则设为0，存在则等于小数点后面位数
  let num = len === -1 ? 0 : str.substring(len+1).length;
  switch (num) {
    case 0:
      str = str + '.00';
      break;
    case 1:
      str = str + '0';
      break;
    case 2:
      break;
    default:
      str = str.slice(0,4)
  }
  return str;
};
export default {
  dealTotal
};
