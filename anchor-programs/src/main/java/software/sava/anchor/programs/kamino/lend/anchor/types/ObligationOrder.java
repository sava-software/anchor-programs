package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

// A single obligation order.
// See [Obligation::orders].
public record ObligationOrder(// A threshold value used by the condition (scaled [Fraction]).
                              // The exact meaning depends on the specific [Self::condition_type].
                              // 
                              // Examples:
                              // - when `condition_type == 2 (UserLtvBelow)`:
                              // then a value of `0.455` here means that the order is active only when the obligation's
                              // user LTV is less than `0.455` (i.e. < 45.5%).
                              // - when `condition_type == 3 (DebtCollPriceRatioAbove)`:
                              // assuming the obligation uses BTC collateral for SOL debt, then a value of `491.3` here
                              // means that the order is active only when the BTC-SOL price is greater than `491.3` (i.e.
                              // > 491.3 SOL per BTC).
                              BigInteger conditionThresholdSf,
                              // A configuration parameter used by the opportunity (scaled [Fraction]).
                              // The exact meaning depends on the specific [Self::opportunity_type].
                              // 
                              // Examples:
                              // - when `opportunity_type == 0 (DeleverageSingleDebtAmount)`:
                              // Assuming the obligation uses BTC collateral for SOL debt, then a value of `1_234_000_000`
                              // here means that a liquidator may repay up to 1234000000 lamports (i.e. 1.234 SOL) on this
                              // obligation.
                              // Note: the special value of [Fraction::MAX] is *not* allowed in this case.
                              // - when `opportunity_type == 1 (DeleverageAllDebtAmount)`:
                              // The only allowed value in this case is [Fraction::MAX] (to emphasize that *all* debt
                              // should be repaid).
                              BigInteger opportunityParameterSf,
                              // A *minimum* additional fraction of collateral transferred to the liquidator, in bps.
                              // 
                              // The minimum bonus is applied exactly when the [Self::condition_threshold_sf] is met, and
                              // grows linearly towards the [Self::max_execution_bonus_bps].
                              // 
                              // Example: a value of `50` here means 50bps == 0.5% bonus for an "LTV > 65%" order, when
                              // executed precisely at the moment LTV exceeds 65%.
                              int minExecutionBonusBps,
                              // A *maximum* additional fraction of collateral transferred to the liquidator, in bps.
                              // 
                              // The maximum bonus is applied at the relevant "extreme" state of the obligation, i.e.:
                              // - for a stop-loss condition, it is a point at which the obligation becomes liquidatable;
                              // - for a take-profit condition, it is a point at which obligation has 0% LTV.
                              // 
                              // In non-extreme states, the actual bonus value is interpolated linearly, starting from
                              // [Self::min_execution_bonus_bps] (at the point specified by the order's condition).
                              // 
                              // Example: a value of `300` here means 300bps == 3.0% bonus for a "debt/coll price > 140"
                              // order, when executed at a higher price = 200, at which the obligation's LTV happens to
                              // be equal to its liquidation LTV.
                              int maxExecutionBonusBps,
                              // Serialized [ConditionType].
                              // The entire order is void when this is zeroed (i.e. representing [ConditionType::Never]).
                              // 
                              // Example: a value of `2` here denotes `UserLtvBelow` condition type. Of course, to
                              // interpret this condition, we also need to take the [Self::condition_threshold_sf] into
                              // account.
                              int conditionType,
                              // Serialized [OpportunityType].
                              // 
                              // Example: a value of `0` here denotes `DeleverageSingleDebtAmount` opportunity. Of course, to
                              // interpret this opportunity, we also need to take the [Self::opportunity_parameter_sf] into
                              // account.
                              int opportunityType,
                              // Internal padding.
                              // The fields above take up 2+2+1+1 bytes = 48 bits, which means we need 80 bits = 10 bytes to
                              // align with `u128`s.
                              byte[] padding1,
                              // End padding.
                              // The total size of a single instance is 8*u128 = 128 bytes.
                              BigInteger[] padding2) implements Borsh {

  public static final int BYTES = 128;
  public static final int PADDING_1_LEN = 10;
  public static final int PADDING_2_LEN = 5;

  public static ObligationOrder read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var conditionThresholdSf = getInt128LE(_data, i);
    i += 16;
    final var opportunityParameterSf = getInt128LE(_data, i);
    i += 16;
    final var minExecutionBonusBps = getInt16LE(_data, i);
    i += 2;
    final var maxExecutionBonusBps = getInt16LE(_data, i);
    i += 2;
    final var conditionType = _data[i] & 0xFF;
    ++i;
    final var opportunityType = _data[i] & 0xFF;
    ++i;
    final var padding1 = new byte[10];
    i += Borsh.readArray(padding1, _data, i);
    final var padding2 = new BigInteger[5];
    Borsh.read128Array(padding2, _data, i);
    return new ObligationOrder(conditionThresholdSf,
                               opportunityParameterSf,
                               minExecutionBonusBps,
                               maxExecutionBonusBps,
                               conditionType,
                               opportunityType,
                               padding1,
                               padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, conditionThresholdSf);
    i += 16;
    putInt128LE(_data, i, opportunityParameterSf);
    i += 16;
    putInt16LE(_data, i, minExecutionBonusBps);
    i += 2;
    putInt16LE(_data, i, maxExecutionBonusBps);
    i += 2;
    _data[i] = (byte) conditionType;
    ++i;
    _data[i] = (byte) opportunityType;
    ++i;
    i += Borsh.writeArrayChecked(padding1, 10, _data, i);
    i += Borsh.write128ArrayChecked(padding2, 5, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
