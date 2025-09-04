package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record DummyZcAccount(PublicKey _address, Discriminator discriminator, PositionBinData positionBinData) implements Borsh {

  public static final int BYTES = 120;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(94, 107, 238, 80, 208, 48, 180, 8);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int POSITION_BIN_DATA_OFFSET = 8;

  public static Filter createPositionBinDataFilter(final PositionBinData positionBinData) {
    return Filter.createMemCompFilter(POSITION_BIN_DATA_OFFSET, positionBinData.write());
  }

  public static DummyZcAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static DummyZcAccount read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static DummyZcAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], DummyZcAccount> FACTORY = DummyZcAccount::read;

  public static DummyZcAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var positionBinData = PositionBinData.read(_data, i);
    return new DummyZcAccount(_address, discriminator, positionBinData);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.write(positionBinData, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
