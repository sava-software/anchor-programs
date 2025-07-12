package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import java.math.BigInteger;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record UpdateDaoParams(OptionalInt passThresholdBps,
                              OptionalLong slotsPerProposal,
                              BigInteger twapInitialObservation,
                              BigInteger twapMaxObservationChangePerUpdate,
                              OptionalLong minQuoteFutarchicLiquidity,
                              OptionalLong minBaseFutarchicLiquidity) implements Borsh {

  public static UpdateDaoParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var passThresholdBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (passThresholdBps.isPresent()) {
      i += 2;
    }
    final var slotsPerProposal = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (slotsPerProposal.isPresent()) {
      i += 8;
    }
    final var twapInitialObservation = _data[i++] == 0 ? null : getInt128LE(_data, i);
    if (twapInitialObservation != null) {
      i += 16;
    }
    final var twapMaxObservationChangePerUpdate = _data[i++] == 0 ? null : getInt128LE(_data, i);
    if (twapMaxObservationChangePerUpdate != null) {
      i += 16;
    }
    final var minQuoteFutarchicLiquidity = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (minQuoteFutarchicLiquidity.isPresent()) {
      i += 8;
    }
    final var minBaseFutarchicLiquidity = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new UpdateDaoParams(passThresholdBps,
                               slotsPerProposal,
                               twapInitialObservation,
                               twapMaxObservationChangePerUpdate,
                               minQuoteFutarchicLiquidity,
                               minBaseFutarchicLiquidity);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptionalshort(passThresholdBps, _data, i);
    i += Borsh.writeOptional(slotsPerProposal, _data, i);
    i += Borsh.write128Optional(twapInitialObservation, _data, i);
    i += Borsh.write128Optional(twapMaxObservationChangePerUpdate, _data, i);
    i += Borsh.writeOptional(minQuoteFutarchicLiquidity, _data, i);
    i += Borsh.writeOptional(minBaseFutarchicLiquidity, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (passThresholdBps == null || passThresholdBps.isEmpty() ? 1 : (1 + 2))
         + (slotsPerProposal == null || slotsPerProposal.isEmpty() ? 1 : (1 + 8))
         + (twapInitialObservation == null ? 1 : (1 + 16))
         + (twapMaxObservationChangePerUpdate == null ? 1 : (1 + 16))
         + (minQuoteFutarchicLiquidity == null || minQuoteFutarchicLiquidity.isEmpty() ? 1 : (1 + 8))
         + (minBaseFutarchicLiquidity == null || minBaseFutarchicLiquidity.isEmpty() ? 1 : (1 + 8));
  }
}
