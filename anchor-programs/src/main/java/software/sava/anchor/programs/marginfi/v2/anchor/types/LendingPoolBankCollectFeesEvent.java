package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getFloat64LE;
import static software.sava.core.encoding.ByteUtil.putFloat64LE;

public record LendingPoolBankCollectFeesEvent(GroupEventHeader header,
                                              PublicKey bank,
                                              PublicKey mint,
                                              double groupFeesCollected,
                                              double groupFeesOutstanding,
                                              double insuranceFeesCollected,
                                              double insuranceFeesOutstanding) implements Borsh {

  public static LendingPoolBankCollectFeesEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var header = GroupEventHeader.read(_data, i);
    i += Borsh.len(header);
    final var bank = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var groupFeesCollected = getFloat64LE(_data, i);
    i += 8;
    final var groupFeesOutstanding = getFloat64LE(_data, i);
    i += 8;
    final var insuranceFeesCollected = getFloat64LE(_data, i);
    i += 8;
    final var insuranceFeesOutstanding = getFloat64LE(_data, i);
    return new LendingPoolBankCollectFeesEvent(header,
                                               bank,
                                               mint,
                                               groupFeesCollected,
                                               groupFeesOutstanding,
                                               insuranceFeesCollected,
                                               insuranceFeesOutstanding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    bank.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    putFloat64LE(_data, i, groupFeesCollected);
    i += 8;
    putFloat64LE(_data, i, groupFeesOutstanding);
    i += 8;
    putFloat64LE(_data, i, insuranceFeesCollected);
    i += 8;
    putFloat64LE(_data, i, insuranceFeesOutstanding);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header)
         + 32
         + 32
         + 8
         + 8
         + 8
         + 8;
  }
}
