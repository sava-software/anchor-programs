package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record AmmConstituentMapping(PublicKey _address,
                                    Discriminator discriminator,
                                    PublicKey lpPool,
                                    int bump,
                                    byte[] padding,
                                    AmmConstituentDatum[] weights) implements Borsh {

  public static final int PADDING_LEN = 3;
  public static final int LP_POOL_OFFSET = 8;
  public static final int BUMP_OFFSET = 40;
  public static final int PADDING_OFFSET = 41;
  public static final int WEIGHTS_OFFSET = 44;

  public static Filter createLpPoolFilter(final PublicKey lpPool) {
    return Filter.createMemCompFilter(LP_POOL_OFFSET, lpPool);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static AmmConstituentMapping read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static AmmConstituentMapping read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static AmmConstituentMapping read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], AmmConstituentMapping> FACTORY = AmmConstituentMapping::read;

  public static AmmConstituentMapping read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var lpPool = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[3];
    i += Borsh.readArray(padding, _data, i);
    final var weights = Borsh.readVector(AmmConstituentDatum.class, AmmConstituentDatum::read, _data, i);
    return new AmmConstituentMapping(_address,
                                     discriminator,
                                     lpPool,
                                     bump,
                                     padding,
                                     weights);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    lpPool.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArrayChecked(padding, 3, _data, i);
    i += Borsh.writeVector(weights, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + 1 + Borsh.lenArray(padding) + Borsh.lenVector(weights);
  }
}
