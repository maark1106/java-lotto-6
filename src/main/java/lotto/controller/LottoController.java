package lotto.controller;

import static lotto.view.InputView.inputBonusNumber;
import static lotto.view.InputView.inputLottoNumber;
import static lotto.view.InputView.inputPurchaseAmount;

import java.util.Map;
import lotto.domain.Lotto;
import lotto.domain.LottoResult;
import lotto.domain.Lottos;
import lotto.domain.User;
import lotto.domain.WinningLotto;
import lotto.service.LottoService;
import lotto.view.OutputView;

public class LottoController {

    private LottoService lottoService = new LottoService();

    public void play() {
        User user = buyTicket();
        Lottos lottos = pickRandomLottos(user.getLottoCount());
        WinningLotto winningLotto = pickWinningLotto();
        long prizeAmount = checkLottoResult(lottos, winningLotto);
        OutputView.printRateOfReturn(user.getBalance(), prizeAmount);
    }

    private User buyTicket() {
        while(true){
            try{
                int purchaseAmount = inputPurchaseAmount();
                return new User(purchaseAmount);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private long checkLottoResult(Lottos lottos, WinningLotto winningLotto) {
        Map<LottoResult, Integer> lottoResult = lottoService.getLottoResult(lottos, winningLotto);
        OutputView.printLottoResult(lottoResult);
        return lottoService.sumTotalLottoPrize(lottoResult);
    }

    private WinningLotto pickWinningLotto() {
        while(true){
            try{
                Lotto lottoAnswer = new Lotto(inputLottoNumber());
                int bonusNumber = inputBonusNumber();
                return new WinningLotto(lottoAnswer, bonusNumber);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private Lottos pickRandomLottos(int lottoCount) {
        Lottos lottos = lottoService.generateLottos(lottoCount);
        OutputView.printPurchaseResult(lottoCount, lottos);
        return lottos;
    }

}
