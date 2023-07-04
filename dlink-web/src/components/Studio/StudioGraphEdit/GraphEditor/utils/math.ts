import { nanoid } from '@reduxjs/toolkit'

/**
 * 返回随机数
 * @returns {number}
 */
export function generateId() {
  return nanoid(6);
}

/**
 * @param time //时间戳

 * @returns {string}//格式化时间
 */
export function formatDate(time: number) {
  if (time.toString().length === 10) {
    time = time * 1000;
  }
  let date = new Date(time);
  let Y = date.getFullYear();
  let M = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
  let D = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
  let H = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
  let Mi = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
  let S = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
  return Y + "-" + M + "-" + D + " " + H + ":" + Mi + ":" + S;

}