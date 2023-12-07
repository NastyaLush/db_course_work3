
-- В нашей системе будут работники, у которых есть свои задачи,
-- соответственно получения задач, относящихся именно к ним необходимая
-- и довольно частая операция.
create index task_by_user_to on task using hash(task_person_to);
-- Поиск задач по статусу необходим для контроля
-- в целом выполнимости задач, так же для получения актуальных задач
-- или задач которые необходимо проверить
create index task_by_status on task using hash(task_status);
-- когда арендуется помещение
-- при подборе помещения необходимо будет проверять
-- арендовано ли оно кем либо
create index place_arend_by_place_id on place_arendator using hash(place_id);
-- при выборе помещения задать фильтры для характеристик
-- поиск помещения по характеристикам одна из основных
-- задач при поиске помещения
create index place_by_char on place_characteristic using hash(characteristic_id);
-- вывести характеристики помещений
-- аналогично вывод характеристик помещения
create index char_by_place on place_characteristic using hash(place_id);
-- поиск по временному диапазону пригодится при
-- поиске помещения, так же при поиске ивента в определенный промежуток
CREATE INDEX idx_place_arendator_time_range ON place_arendator (from_time, to_time);
