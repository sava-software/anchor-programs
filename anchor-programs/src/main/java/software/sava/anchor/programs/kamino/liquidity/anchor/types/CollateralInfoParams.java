package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CollateralInfoParams(PublicKey mint,
                                   long lowerHeuristic,
                                   long upperHeuristic,
                                   long expHeuristic,
                                   long maxTwapDivergenceBps,
                                   short[] scopeTwapPriceChain,
                                   short[] scopePriceChain,
                                   byte[] name,
                                   long maxAgePriceSeconds,
                                   long maxAgeTwapSeconds,
                                   long maxIgnorableAmountAsReward,
                                   int disabled,
                                   short[] scopeStakingRateChain) implements Borsh {

  public static final int BYTES = 145;
  public static final int SCOPE_TWAP_PRICE_CHAIN_LEN = 4;
  public static final int SCOPE_PRICE_CHAIN_LEN = 4;
  public static final int NAME_LEN = 32;
  public static final int SCOPE_STAKING_RATE_CHAIN_LEN = 4;

  public static CollateralInfoParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var lowerHeuristic = getInt64LE(_data, i);
    i += 8;
    final var upperHeuristic = getInt64LE(_data, i);
    i += 8;
    final var expHeuristic = getInt64LE(_data, i);
    i += 8;
    final var maxTwapDivergenceBps = getInt64LE(_data, i);
    i += 8;
    final var scopeTwapPriceChain = new short[4];
    i += Borsh.readArray(scopeTwapPriceChain, _data, i);
    final var scopePriceChain = new short[4];
    i += Borsh.readArray(scopePriceChain, _data, i);
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var maxAgePriceSeconds = getInt64LE(_data, i);
    i += 8;
    final var maxAgeTwapSeconds = getInt64LE(_data, i);
    i += 8;
    final var maxIgnorableAmountAsReward = getInt64LE(_data, i);
    i += 8;
    final var disabled = _data[i] & 0xFF;
    ++i;
    final var scopeStakingRateChain = new short[4];
    Borsh.readArray(scopeStakingRateChain, _data, i);
    return new CollateralInfoParams(mint,
                                    lowerHeuristic,
                                    upperHeuristic,
                                    expHeuristic,
                                    maxTwapDivergenceBps,
                                    scopeTwapPriceChain,
                                    scopePriceChain,
                                    name,
                                    maxAgePriceSeconds,
                                    maxAgeTwapSeconds,
                                    maxIgnorableAmountAsReward,
                                    disabled,
                                    scopeStakingRateChain);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    mint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lowerHeuristic);
    i += 8;
    putInt64LE(_data, i, upperHeuristic);
    i += 8;
    putInt64LE(_data, i, expHeuristic);
    i += 8;
    putInt64LE(_data, i, maxTwapDivergenceBps);
    i += 8;
    i += Borsh.writeArray(scopeTwapPriceChain, _data, i);
    i += Borsh.writeArray(scopePriceChain, _data, i);
    i += Borsh.writeArray(name, _data, i);
    putInt64LE(_data, i, maxAgePriceSeconds);
    i += 8;
    putInt64LE(_data, i, maxAgeTwapSeconds);
    i += 8;
    putInt64LE(_data, i, maxIgnorableAmountAsReward);
    i += 8;
    _data[i] = (byte) disabled;
    ++i;
    i += Borsh.writeArray(scopeStakingRateChain, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
