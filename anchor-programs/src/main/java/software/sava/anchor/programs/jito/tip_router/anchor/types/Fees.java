package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Fees(long activationEpoch,
                   Fee priorityFeeDistributionFeeBps,
                   byte[] reserved,
                   Fee[] baseFeeGroupsBps,
                   Fee[] ncnFeeGroupsBps) implements Borsh {

  public static final int BYTES = 168;

  public static Fees read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var activationEpoch = getInt64LE(_data, i);
    i += 8;
    final var priorityFeeDistributionFeeBps = Fee.read(_data, i);
    i += Borsh.len(priorityFeeDistributionFeeBps);
    final var reserved = new byte[126];
    i += Borsh.readArray(reserved, _data, i);
    final var baseFeeGroupsBps = new Fee[8];
    i += Borsh.readArray(baseFeeGroupsBps, Fee::read, _data, i);
    final var ncnFeeGroupsBps = new Fee[8];
    Borsh.readArray(ncnFeeGroupsBps, Fee::read, _data, i);
    return new Fees(activationEpoch,
                    priorityFeeDistributionFeeBps,
                    reserved,
                    baseFeeGroupsBps,
                    ncnFeeGroupsBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, activationEpoch);
    i += 8;
    i += Borsh.write(priorityFeeDistributionFeeBps, _data, i);
    i += Borsh.writeArray(reserved, _data, i);
    i += Borsh.writeArray(baseFeeGroupsBps, _data, i);
    i += Borsh.writeArray(ncnFeeGroupsBps, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
