package juja.domain.service;

import juja.domain.dao.ProgressDao;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;

public class ProgressServiceImpl implements ProgressService {

    ProgressDao progressDao;
    private Set<String> codesBlackList = Collections.emptySet();

    @Override
    public Set<String> fetchProgressCodes() {
        return progressDao.fetchProgressCodes().stream().
                filter(code -> !codesBlackList.contains(code)).collect(toSet());
    }

    public void setCodesBlackList(String codesBlackList) {
        this.codesBlackList = new HashSet<>(asList(codesBlackList.split(";")));
    }
}
