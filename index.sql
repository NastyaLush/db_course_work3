-- искать таски для конкретного пользователя, нет операций сравнение и т.д.
create index task_by_user_to on task using hash(task_person_to);
-- искать таски по статусу
create index task_by_status on task using hash(task_status);
-- пользователи какой-то фракции
create index persons_by_fraction on person using hash(person_fraction_id);
-- отчеты по загаловкам
create index reports_by_title on report(report_title);
-- отчеты по создателю
create index reports_by_created_by on report(report_created_by);
-- отчеты по дате
create index reports_by_created_at on report(created_at);
-- поиск мероприятия по определенно дате
--create index event_by_date on event_place(event_date);
-- когда арендуется помещение
create index place_arend_by_place_id on place_arendator using hash(place_id);
-- при выборе помещения задать фильтры для характеристик
create index place_by_char on place_characteristic using hash(characteristic_id);
-- вывести характеристики помещений
create index char_by_place on place_characteristic using hash(place_id);

--расписать индексы в рамках предметной области