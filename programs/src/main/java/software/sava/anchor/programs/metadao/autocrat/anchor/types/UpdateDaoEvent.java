package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UpdateDaoEvent(CommonFields common,
                             PublicKey dao,
                             int passThresholdBps,
                             long slotsPerProposal,
                             BigInteger twapInitialObservation,
                             BigInteger twapMaxObservationChangePerUpdate,
                             long minQuoteFutarchicLiquidity,
                             long minBaseFutarchicLiquidity) implements Borsh {

  public static final int BYTES = 106;

  public static UpdateDaoEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var dao = readPubKey(_data, i);
    i += 32;
    final var passThresholdBps = getInt16LE(_data, i);
    i += 2;
    final var slotsPerProposal = getInt64LE(_data, i);
    i += 8;
    final var twapInitialObservation = getInt128LE(_data, i);
    i += 16;
    final var twapMaxObservationChangePerUpdate = getInt128LE(_data, i);
    i += 16;
    final var minQuoteFutarchicLiquidity = getInt64LE(_data, i);
    i += 8;
    final var minBaseFutarchicLiquidity = getInt64LE(_data, i);
    return new UpdateDaoEvent(common,
                              dao,
                              passThresholdBps,
                              slotsPerProposal,
                              twapInitialObservation,
                              twapMaxObservationChangePerUpdate,
                              minQuoteFutarchicLiquidity,
                              minBaseFutarchicLiquidity);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    dao.write(_data, i);
    i += 32;
    putInt16LE(_data, i, passThresholdBps);
    i += 2;
    putInt64LE(_data, i, slotsPerProposal);
    i += 8;
    putInt128LE(_data, i, twapInitialObservation);
    i += 16;
    putInt128LE(_data, i, twapMaxObservationChangePerUpdate);
    i += 16;
    putInt64LE(_data, i, minQuoteFutarchicLiquidity);
    i += 8;
    putInt64LE(_data, i, minBaseFutarchicLiquidity);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
