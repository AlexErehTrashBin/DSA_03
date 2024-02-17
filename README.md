# Task 3 - Задача о восьми ферзях

## ***Условие:***

Найти взаимное расположение 8-ми ферзей на шахматной доске такое, чтобы ферзи
не «били» друг друга. Рекурсию не использовать, но можно использовать стек.

## ***Особенности реализации:***

Сделано через стек, как и сказано в задании, но в остальном максимально тупо:
находятся ***всего лишь*** 28 (из 92 возможных) выигрышных положений, из которых только 7 оригинальных
(из-за поворотов доски при выигрышном положении, что также генерирует правильные решения).
Искать больше одного решения в задании вроде не требуется, но всё же ¯\_(ツ)_/¯

// TODO: Стек из целых чисел с преобразованием в массив 