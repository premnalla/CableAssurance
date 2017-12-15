SELECT unique mta.* from market m join blade b using (market_res_id) 
join cmts c using (blade_res_id) join channel ch using (cmts_res_id) join 
cable_modem cm using (channel_res_id) join emta mta using (cm_res_id) join 
alarm_basic a on (mta.emta_res_id=a.resource_res_id) where m.market_res_id=4;

