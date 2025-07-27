package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitializeDaoParams(BigInteger twapInitialObservation,
                                  BigInteger twapMaxObservationChangePerUpdate,
                                  long twapStartDelaySlots,
                                  long minQuoteFutarchicLiquidity,
                                  long minBaseFutarchicLiquidity,
                                  int passThresholdBps,
                                  long slotsPerProposal,
                                  long nonce,
                                  InitialSpendingLimit initialSpendingLimit) implements Borsh {

  public static InitializeDaoParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var twapInitialObservation = getInt128LE(_data, i);
    i += 16;
    final var twapMaxObservationChangePerUpdate = getInt128LE(_data, i);
    i += 16;
    final var twapStartDelaySlots = getInt64LE(_data, i);
    i += 8;
    final var minQuoteFutarchicLiquidity = getInt64LE(_data, i);
    i += 8;
    final var minBaseFutarchicLiquidity = getInt64LE(_data, i);
    i += 8;
    final var passThresholdBps = getInt16LE(_data, i);
    i += 2;
    final var slotsPerProposal = getInt64LE(_data, i);
    i += 8;
    final var nonce = getInt64LE(_data, i);
    i += 8;
    final var initialSpendingLimit = _data[i++] == 0 ? null : InitialSpendingLimit.read(_data, i);
    return new InitializeDaoParams(twapInitialObservation,
                                   twapMaxObservationChangePerUpdate,
                                   twapStartDelaySlots,
                                   minQuoteFutarchicLiquidity,
                                   minBaseFutarchicLiquidity,
                                   passThresholdBps,
                                   slotsPerProposal,
                                   nonce,
                                   initialSpendingLimit);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, twapInitialObservation);
    i += 16;
    putInt128LE(_data, i, twapMaxObservationChangePerUpdate);
    i += 16;
    putInt64LE(_data, i, twapStartDelaySlots);
    i += 8;
    putInt64LE(_data, i, minQuoteFutarchicLiquidity);
    i += 8;
    putInt64LE(_data, i, minBaseFutarchicLiquidity);
    i += 8;
    putInt16LE(_data, i, passThresholdBps);
    i += 2;
    putInt64LE(_data, i, slotsPerProposal);
    i += 8;
    putInt64LE(_data, i, nonce);
    i += 8;
    i += Borsh.writeOptional(initialSpendingLimit, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 16
         + 16
         + 8
         + 8
         + 8
         + 2
         + 8
         + 8
         + (initialSpendingLimit == null ? 1 : (1 + Borsh.len(initialSpendingLimit)));
  }
}
