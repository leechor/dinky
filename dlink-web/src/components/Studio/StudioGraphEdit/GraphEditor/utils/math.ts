import {nanoid} from '@reduxjs/toolkit'

/**
 * 返回随机数
 * @returns {number}
 */
export function generateId() {
  return nanoid(6);
}
