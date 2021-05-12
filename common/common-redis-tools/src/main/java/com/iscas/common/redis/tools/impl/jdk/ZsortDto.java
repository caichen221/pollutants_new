package com.iscas.common.redis.tools.impl.jdk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/11 20:18
 * @since jdk1.8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZsortDto implements Comparable<ZsortDto> {
    private double score = Double.MIN_VALUE;
    private Object member;

    @Override
    public int compareTo(ZsortDto o) {
        if (Objects.equals(o.getMember(), member)) {
            return 0;
        }
        return o.score < score ? -1 : 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZsortDto zsortDto = (ZsortDto) o;
        return Objects.equals(member, zsortDto.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member);
    }




}
