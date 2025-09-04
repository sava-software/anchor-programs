package software.sava.anchor.programs.kamino.vaults.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface KaminoVaultError extends ProgramError permits
    KaminoVaultError.DepositAmountsZero,
    KaminoVaultError.SharesIssuedAmountDoesNotMatch,
    KaminoVaultError.MathOverflow,
    KaminoVaultError.IntegerOverflow,
    KaminoVaultError.WithdrawAmountBelowMinimum,
    KaminoVaultError.TooMuchLiquidityToWithdraw,
    KaminoVaultError.ReserveAlreadyExists,
    KaminoVaultError.ReserveNotPartOfAllocations,
    KaminoVaultError.CouldNotDeserializeAccountAsReserve,
    KaminoVaultError.ReserveNotProvidedInTheAccounts,
    KaminoVaultError.ReserveAccountAndKeyMismatch,
    KaminoVaultError.OutOfRangeOfReserveIndex,
    KaminoVaultError.CannotFindReserveInAllocations,
    KaminoVaultError.InvestAmountBelowMinimum,
    KaminoVaultError.AdminAuthorityIncorrect,
    KaminoVaultError.BaseVaultAuthorityIncorrect,
    KaminoVaultError.BaseVaultAuthorityBumpIncorrect,
    KaminoVaultError.TokenMintIncorrect,
    KaminoVaultError.TokenMintDecimalsIncorrect,
    KaminoVaultError.TokenVaultIncorrect,
    KaminoVaultError.SharesMintDecimalsIncorrect,
    KaminoVaultError.SharesMintIncorrect,
    KaminoVaultError.InitialAccountingIncorrect,
    KaminoVaultError.ReserveIsStale,
    KaminoVaultError.NotEnoughLiquidityDisinvestedToSendToUser,
    KaminoVaultError.BPSValueTooBig,
    KaminoVaultError.DepositAmountBelowMinimum,
    KaminoVaultError.ReserveSpaceExhausted,
    KaminoVaultError.CannotWithdrawFromEmptyVault,
    KaminoVaultError.TokensDepositedAmountDoesNotMatch,
    KaminoVaultError.AmountToWithdrawDoesNotMatch,
    KaminoVaultError.LiquidityToWithdrawDoesNotMatch,
    KaminoVaultError.UserReceivedAmountDoesNotMatch,
    KaminoVaultError.SharesBurnedAmountDoesNotMatch,
    KaminoVaultError.DisinvestedLiquidityAmountDoesNotMatch,
    KaminoVaultError.SharesMintedAmountDoesNotMatch,
    KaminoVaultError.AUMDecreasedAfterInvest,
    KaminoVaultError.AUMBelowPendingFees,
    KaminoVaultError.DepositAmountsZeroShares,
    KaminoVaultError.WithdrawResultsInZeroShares,
    KaminoVaultError.CannotWithdrawZeroShares,
    KaminoVaultError.ManagementFeeGreaterThanMaxAllowed,
    KaminoVaultError.VaultAUMZero,
    KaminoVaultError.MissingReserveForBatchRefresh,
    KaminoVaultError.MinWithdrawAmountTooBig,
    KaminoVaultError.InvestTooSoon,
    KaminoVaultError.WrongAdminOrAllocationAdmin,
    KaminoVaultError.ReserveHasNonZeroAllocationOrCTokens,
    KaminoVaultError.DepositAmountGreaterThanRequestedAmount {

  static KaminoVaultError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 7000 -> DepositAmountsZero.INSTANCE;
      case 7001 -> SharesIssuedAmountDoesNotMatch.INSTANCE;
      case 7002 -> MathOverflow.INSTANCE;
      case 7003 -> IntegerOverflow.INSTANCE;
      case 7004 -> WithdrawAmountBelowMinimum.INSTANCE;
      case 7005 -> TooMuchLiquidityToWithdraw.INSTANCE;
      case 7006 -> ReserveAlreadyExists.INSTANCE;
      case 7007 -> ReserveNotPartOfAllocations.INSTANCE;
      case 7008 -> CouldNotDeserializeAccountAsReserve.INSTANCE;
      case 7009 -> ReserveNotProvidedInTheAccounts.INSTANCE;
      case 7010 -> ReserveAccountAndKeyMismatch.INSTANCE;
      case 7011 -> OutOfRangeOfReserveIndex.INSTANCE;
      case 7012 -> CannotFindReserveInAllocations.INSTANCE;
      case 7013 -> InvestAmountBelowMinimum.INSTANCE;
      case 7014 -> AdminAuthorityIncorrect.INSTANCE;
      case 7015 -> BaseVaultAuthorityIncorrect.INSTANCE;
      case 7016 -> BaseVaultAuthorityBumpIncorrect.INSTANCE;
      case 7017 -> TokenMintIncorrect.INSTANCE;
      case 7018 -> TokenMintDecimalsIncorrect.INSTANCE;
      case 7019 -> TokenVaultIncorrect.INSTANCE;
      case 7020 -> SharesMintDecimalsIncorrect.INSTANCE;
      case 7021 -> SharesMintIncorrect.INSTANCE;
      case 7022 -> InitialAccountingIncorrect.INSTANCE;
      case 7023 -> ReserveIsStale.INSTANCE;
      case 7024 -> NotEnoughLiquidityDisinvestedToSendToUser.INSTANCE;
      case 7025 -> BPSValueTooBig.INSTANCE;
      case 7026 -> DepositAmountBelowMinimum.INSTANCE;
      case 7027 -> ReserveSpaceExhausted.INSTANCE;
      case 7028 -> CannotWithdrawFromEmptyVault.INSTANCE;
      case 7029 -> TokensDepositedAmountDoesNotMatch.INSTANCE;
      case 7030 -> AmountToWithdrawDoesNotMatch.INSTANCE;
      case 7031 -> LiquidityToWithdrawDoesNotMatch.INSTANCE;
      case 7032 -> UserReceivedAmountDoesNotMatch.INSTANCE;
      case 7033 -> SharesBurnedAmountDoesNotMatch.INSTANCE;
      case 7034 -> DisinvestedLiquidityAmountDoesNotMatch.INSTANCE;
      case 7035 -> SharesMintedAmountDoesNotMatch.INSTANCE;
      case 7036 -> AUMDecreasedAfterInvest.INSTANCE;
      case 7037 -> AUMBelowPendingFees.INSTANCE;
      case 7038 -> DepositAmountsZeroShares.INSTANCE;
      case 7039 -> WithdrawResultsInZeroShares.INSTANCE;
      case 7040 -> CannotWithdrawZeroShares.INSTANCE;
      case 7041 -> ManagementFeeGreaterThanMaxAllowed.INSTANCE;
      case 7042 -> VaultAUMZero.INSTANCE;
      case 7043 -> MissingReserveForBatchRefresh.INSTANCE;
      case 7044 -> MinWithdrawAmountTooBig.INSTANCE;
      case 7045 -> InvestTooSoon.INSTANCE;
      case 7046 -> WrongAdminOrAllocationAdmin.INSTANCE;
      case 7047 -> ReserveHasNonZeroAllocationOrCTokens.INSTANCE;
      case 7048 -> DepositAmountGreaterThanRequestedAmount.INSTANCE;
      default -> throw new IllegalStateException("Unexpected KaminoVault error code: " + errorCode);
    };
  }

  record DepositAmountsZero(int code, String msg) implements KaminoVaultError {

    public static final DepositAmountsZero INSTANCE = new DepositAmountsZero(
        7000, "DepositAmountsZero"
    );
  }

  record SharesIssuedAmountDoesNotMatch(int code, String msg) implements KaminoVaultError {

    public static final SharesIssuedAmountDoesNotMatch INSTANCE = new SharesIssuedAmountDoesNotMatch(
        7001, "SharesIssuedAmountDoesNotMatch"
    );
  }

  record MathOverflow(int code, String msg) implements KaminoVaultError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        7002, "MathOverflow"
    );
  }

  record IntegerOverflow(int code, String msg) implements KaminoVaultError {

    public static final IntegerOverflow INSTANCE = new IntegerOverflow(
        7003, "IntegerOverflow"
    );
  }

  record WithdrawAmountBelowMinimum(int code, String msg) implements KaminoVaultError {

    public static final WithdrawAmountBelowMinimum INSTANCE = new WithdrawAmountBelowMinimum(
        7004, "Withdrawn amount is below minimum"
    );
  }

  record TooMuchLiquidityToWithdraw(int code, String msg) implements KaminoVaultError {

    public static final TooMuchLiquidityToWithdraw INSTANCE = new TooMuchLiquidityToWithdraw(
        7005, "TooMuchLiquidityToWithdraw"
    );
  }

  record ReserveAlreadyExists(int code, String msg) implements KaminoVaultError {

    public static final ReserveAlreadyExists INSTANCE = new ReserveAlreadyExists(
        7006, "ReserveAlreadyExists"
    );
  }

  record ReserveNotPartOfAllocations(int code, String msg) implements KaminoVaultError {

    public static final ReserveNotPartOfAllocations INSTANCE = new ReserveNotPartOfAllocations(
        7007, "ReserveNotPartOfAllocations"
    );
  }

  record CouldNotDeserializeAccountAsReserve(int code, String msg) implements KaminoVaultError {

    public static final CouldNotDeserializeAccountAsReserve INSTANCE = new CouldNotDeserializeAccountAsReserve(
        7008, "CouldNotDeserializeAccountAsReserve"
    );
  }

  record ReserveNotProvidedInTheAccounts(int code, String msg) implements KaminoVaultError {

    public static final ReserveNotProvidedInTheAccounts INSTANCE = new ReserveNotProvidedInTheAccounts(
        7009, "ReserveNotProvidedInTheAccounts"
    );
  }

  record ReserveAccountAndKeyMismatch(int code, String msg) implements KaminoVaultError {

    public static final ReserveAccountAndKeyMismatch INSTANCE = new ReserveAccountAndKeyMismatch(
        7010, "ReserveAccountAndKeyMismatch"
    );
  }

  record OutOfRangeOfReserveIndex(int code, String msg) implements KaminoVaultError {

    public static final OutOfRangeOfReserveIndex INSTANCE = new OutOfRangeOfReserveIndex(
        7011, "OutOfRangeOfReserveIndex"
    );
  }

  record CannotFindReserveInAllocations(int code, String msg) implements KaminoVaultError {

    public static final CannotFindReserveInAllocations INSTANCE = new CannotFindReserveInAllocations(
        7012, "OutOfRangeOfReserveIndex"
    );
  }

  record InvestAmountBelowMinimum(int code, String msg) implements KaminoVaultError {

    public static final InvestAmountBelowMinimum INSTANCE = new InvestAmountBelowMinimum(
        7013, "Invested amount is below minimum"
    );
  }

  record AdminAuthorityIncorrect(int code, String msg) implements KaminoVaultError {

    public static final AdminAuthorityIncorrect INSTANCE = new AdminAuthorityIncorrect(
        7014, "AdminAuthorityIncorrect"
    );
  }

  record BaseVaultAuthorityIncorrect(int code, String msg) implements KaminoVaultError {

    public static final BaseVaultAuthorityIncorrect INSTANCE = new BaseVaultAuthorityIncorrect(
        7015, "BaseVaultAuthorityIncorrect"
    );
  }

  record BaseVaultAuthorityBumpIncorrect(int code, String msg) implements KaminoVaultError {

    public static final BaseVaultAuthorityBumpIncorrect INSTANCE = new BaseVaultAuthorityBumpIncorrect(
        7016, "BaseVaultAuthorityBumpIncorrect"
    );
  }

  record TokenMintIncorrect(int code, String msg) implements KaminoVaultError {

    public static final TokenMintIncorrect INSTANCE = new TokenMintIncorrect(
        7017, "TokenMintIncorrect"
    );
  }

  record TokenMintDecimalsIncorrect(int code, String msg) implements KaminoVaultError {

    public static final TokenMintDecimalsIncorrect INSTANCE = new TokenMintDecimalsIncorrect(
        7018, "TokenMintDecimalsIncorrect"
    );
  }

  record TokenVaultIncorrect(int code, String msg) implements KaminoVaultError {

    public static final TokenVaultIncorrect INSTANCE = new TokenVaultIncorrect(
        7019, "TokenVaultIncorrect"
    );
  }

  record SharesMintDecimalsIncorrect(int code, String msg) implements KaminoVaultError {

    public static final SharesMintDecimalsIncorrect INSTANCE = new SharesMintDecimalsIncorrect(
        7020, "SharesMintDecimalsIncorrect"
    );
  }

  record SharesMintIncorrect(int code, String msg) implements KaminoVaultError {

    public static final SharesMintIncorrect INSTANCE = new SharesMintIncorrect(
        7021, "SharesMintIncorrect"
    );
  }

  record InitialAccountingIncorrect(int code, String msg) implements KaminoVaultError {

    public static final InitialAccountingIncorrect INSTANCE = new InitialAccountingIncorrect(
        7022, "InitialAccountingIncorrect"
    );
  }

  record ReserveIsStale(int code, String msg) implements KaminoVaultError {

    public static final ReserveIsStale INSTANCE = new ReserveIsStale(
        7023, "Reserve is stale and must be refreshed before any operation"
    );
  }

  record NotEnoughLiquidityDisinvestedToSendToUser(int code, String msg) implements KaminoVaultError {

    public static final NotEnoughLiquidityDisinvestedToSendToUser INSTANCE = new NotEnoughLiquidityDisinvestedToSendToUser(
        7024, "Not enough liquidity disinvested to send to user"
    );
  }

  record BPSValueTooBig(int code, String msg) implements KaminoVaultError {

    public static final BPSValueTooBig INSTANCE = new BPSValueTooBig(
        7025, "BPS value is greater than 10000"
    );
  }

  record DepositAmountBelowMinimum(int code, String msg) implements KaminoVaultError {

    public static final DepositAmountBelowMinimum INSTANCE = new DepositAmountBelowMinimum(
        7026, "Deposited amount is below minimum"
    );
  }

  record ReserveSpaceExhausted(int code, String msg) implements KaminoVaultError {

    public static final ReserveSpaceExhausted INSTANCE = new ReserveSpaceExhausted(
        7027, "Vault have no space for new reserves"
    );
  }

  record CannotWithdrawFromEmptyVault(int code, String msg) implements KaminoVaultError {

    public static final CannotWithdrawFromEmptyVault INSTANCE = new CannotWithdrawFromEmptyVault(
        7028, "Cannot withdraw from empty vault"
    );
  }

  record TokensDepositedAmountDoesNotMatch(int code, String msg) implements KaminoVaultError {

    public static final TokensDepositedAmountDoesNotMatch INSTANCE = new TokensDepositedAmountDoesNotMatch(
        7029, "TokensDepositedAmountDoesNotMatch"
    );
  }

  record AmountToWithdrawDoesNotMatch(int code, String msg) implements KaminoVaultError {

    public static final AmountToWithdrawDoesNotMatch INSTANCE = new AmountToWithdrawDoesNotMatch(
        7030, "Amount to withdraw does not match"
    );
  }

  record LiquidityToWithdrawDoesNotMatch(int code, String msg) implements KaminoVaultError {

    public static final LiquidityToWithdrawDoesNotMatch INSTANCE = new LiquidityToWithdrawDoesNotMatch(
        7031, "Liquidity to withdraw does not match"
    );
  }

  record UserReceivedAmountDoesNotMatch(int code, String msg) implements KaminoVaultError {

    public static final UserReceivedAmountDoesNotMatch INSTANCE = new UserReceivedAmountDoesNotMatch(
        7032, "User received amount does not match"
    );
  }

  record SharesBurnedAmountDoesNotMatch(int code, String msg) implements KaminoVaultError {

    public static final SharesBurnedAmountDoesNotMatch INSTANCE = new SharesBurnedAmountDoesNotMatch(
        7033, "Shares burned amount does not match"
    );
  }

  record DisinvestedLiquidityAmountDoesNotMatch(int code, String msg) implements KaminoVaultError {

    public static final DisinvestedLiquidityAmountDoesNotMatch INSTANCE = new DisinvestedLiquidityAmountDoesNotMatch(
        7034, "Disinvested liquidity amount does not match"
    );
  }

  record SharesMintedAmountDoesNotMatch(int code, String msg) implements KaminoVaultError {

    public static final SharesMintedAmountDoesNotMatch INSTANCE = new SharesMintedAmountDoesNotMatch(
        7035, "SharesMintedAmountDoesNotMatch"
    );
  }

  record AUMDecreasedAfterInvest(int code, String msg) implements KaminoVaultError {

    public static final AUMDecreasedAfterInvest INSTANCE = new AUMDecreasedAfterInvest(
        7036, "AUM decreased after invest"
    );
  }

  record AUMBelowPendingFees(int code, String msg) implements KaminoVaultError {

    public static final AUMBelowPendingFees INSTANCE = new AUMBelowPendingFees(
        7037, "AUM is below pending fees"
    );
  }

  record DepositAmountsZeroShares(int code, String msg) implements KaminoVaultError {

    public static final DepositAmountsZeroShares INSTANCE = new DepositAmountsZeroShares(
        7038, "Deposit amount results in 0 shares"
    );
  }

  record WithdrawResultsInZeroShares(int code, String msg) implements KaminoVaultError {

    public static final WithdrawResultsInZeroShares INSTANCE = new WithdrawResultsInZeroShares(
        7039, "Withdraw amount results in 0 shares"
    );
  }

  record CannotWithdrawZeroShares(int code, String msg) implements KaminoVaultError {

    public static final CannotWithdrawZeroShares INSTANCE = new CannotWithdrawZeroShares(
        7040, "Cannot withdraw zero shares"
    );
  }

  record ManagementFeeGreaterThanMaxAllowed(int code, String msg) implements KaminoVaultError {

    public static final ManagementFeeGreaterThanMaxAllowed INSTANCE = new ManagementFeeGreaterThanMaxAllowed(
        7041, "Management fee is greater than maximum allowed"
    );
  }

  record VaultAUMZero(int code, String msg) implements KaminoVaultError {

    public static final VaultAUMZero INSTANCE = new VaultAUMZero(
        7042, "Vault assets under management are empty"
    );
  }

  record MissingReserveForBatchRefresh(int code, String msg) implements KaminoVaultError {

    public static final MissingReserveForBatchRefresh INSTANCE = new MissingReserveForBatchRefresh(
        7043, "Missing reserve for batch refresh"
    );
  }

  record MinWithdrawAmountTooBig(int code, String msg) implements KaminoVaultError {

    public static final MinWithdrawAmountTooBig INSTANCE = new MinWithdrawAmountTooBig(
        7044, "Min withdraw amount is too big"
    );
  }

  record InvestTooSoon(int code, String msg) implements KaminoVaultError {

    public static final InvestTooSoon INSTANCE = new InvestTooSoon(
        7045, "Invest is called too soon after last invest"
    );
  }

  record WrongAdminOrAllocationAdmin(int code, String msg) implements KaminoVaultError {

    public static final WrongAdminOrAllocationAdmin INSTANCE = new WrongAdminOrAllocationAdmin(
        7046, "Wrong admin or allocation admin"
    );
  }

  record ReserveHasNonZeroAllocationOrCTokens(int code, String msg) implements KaminoVaultError {

    public static final ReserveHasNonZeroAllocationOrCTokens INSTANCE = new ReserveHasNonZeroAllocationOrCTokens(
        7047, "Reserve has non-zero allocation or ctokens so cannot be removed"
    );
  }

  record DepositAmountGreaterThanRequestedAmount(int code, String msg) implements KaminoVaultError {

    public static final DepositAmountGreaterThanRequestedAmount INSTANCE = new DepositAmountGreaterThanRequestedAmount(
        7048, "Deposit amount is greater than requested amount"
    );
  }
}
