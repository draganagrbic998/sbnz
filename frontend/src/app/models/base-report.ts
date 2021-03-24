interface ReportUnit{
    activeCnt: number;
    closedCnt: number;
    abortedShare: number;
    baseAvg: number;
    monthsAvg: number;
    increaseAvg: number;
    renewAvg: number;
}

export interface BaseReport{
    rsd: ReportUnit;
    eur: ReportUnit;
    usd: ReportUnit;
    chf: ReportUnit;
    gbp: ReportUnit;
}
