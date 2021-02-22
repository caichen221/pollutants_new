package com.iscas.biz.service.common;

import com.iscas.biz.domain.common.Opration;
import com.iscas.biz.mapper.common.OprationMapper;
import com.iscas.templet.view.table.ComboboxData;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限操作service
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/22 18:06
 * @since jdk1.8
 */
@Service
public class OprationService {
    private final OprationMapper oprationMapper;

    public OprationService(OprationMapper oprationMapper) {
        this.oprationMapper = oprationMapper;
    }

    public List<ComboboxData<Opration>> combobox() {
        List<Opration> oprations = oprationMapper.selectByExample(null);
        List<ComboboxData<Opration>> comboboxDatas = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(oprations)) {
            comboboxDatas = oprations.stream().map(opration -> {
                ComboboxData<Opration> comboboxData = new ComboboxData<>();
                comboboxData.setLabel(opration.getOpName());
                comboboxData.setValue(opration.getOpName());
                comboboxData.setData(opration);
                return comboboxData;
            }).collect(Collectors.toList());
        }
        return comboboxDatas;
    }
}
