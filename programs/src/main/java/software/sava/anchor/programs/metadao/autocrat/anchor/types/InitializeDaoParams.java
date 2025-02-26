package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import java.math.BigInteger;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitializeDaoParams(BigInteger twapInitialObservation,
                                  BigInteger twapMaxObservationChangePerUpdate,
                                  long minQuoteFutarchicLiquidity,
                                  long minBaseFutarchicLiquidity,
                                  OptionalInt passThresholdBps,
                                  OptionalLong slotsPerProposal) implements Borsh {

  public static InitializeDaoParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var twapInitialObservation = getInt128LE(_data, i);
    i += 16;
    final var twapMaxObservationChangePerUpdate = getInt128LE(_data, i);
    i += 16;
    final var minQuoteFutarchicLiquidity = getInt64LE(_data, i);
    i += 8;
    final var minBaseFutarchicLiquidity = getInt64LE(_data, i);
    i += 8;
    final var passThresholdBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (passThresholdBps.isPresent()) {
      i += 2;
    }
    final var slotsPerProposal = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new InitializeDaoParams(twapInitialObservation,
                                   twapMaxObservationChangePerUpdate,
                                   minQuoteFutarchicLiquidity,
                                   minBaseFutarchicLiquidity,
                                   passThresholdBps,
                                   slotsPerProposal);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, twapInitialObservation);
    i += 16;
    putInt128LE(_data, i, twapMaxObservationChangePerUpdate);
    i += 16;
    putInt64LE(_data, i, minQuoteFutarchicLiquidity);
    i += 8;
    putInt64LE(_data, i, minBaseFutarchicLiquidity);
    i += 8;
    i += Borsh.writeOptionalshort(passThresholdBps, _data, i);
    i += Borsh.writeOptional(slotsPerProposal, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 16
         + 16
         + 8
         + 8
         + (passThresholdBps == null || passThresholdBps.isEmpty() ? 1 : (1 + 2))
         + (slotsPerProposal == null || slotsPerProposal.isEmpty() ? 1 : (1 + 8));
  }
}
