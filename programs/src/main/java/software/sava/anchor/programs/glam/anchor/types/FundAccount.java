package software.sava.anchor.programs.glam.anchor.types;

import java.lang.String;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record FundAccount(PublicKey _address,
                          Discriminator discriminator,
                          String name, byte[] _name,
                          String uri, byte[] _uri,
                          PublicKey treasury,
                          PublicKey[] shareClasses,
                          PublicKey openfunds,
                          String openfundsUri, byte[] _openfundsUri,
                          PublicKey manager,
                          PublicKey engine,
                          EngineField[][] params) implements Borsh {

  public static final int NAME_OFFSET = 8;

  public static Filter createNameFilter(final String name) {
    final byte[] bytes = name.getBytes(UTF_8);
    final byte[] _data = new byte[4 + bytes.length];
    Borsh.write(bytes, _data, 0);
    return Filter.createMemCompFilter(NAME_OFFSET, _data);
  }

  public static FundAccount createRecord(final PublicKey _address,
                                         final Discriminator discriminator,
                                         final String name,
                                         final String uri,
                                         final PublicKey treasury,
                                         final PublicKey[] shareClasses,
                                         final PublicKey openfunds,
                                         final String openfundsUri,
                                         final PublicKey manager,
                                         final PublicKey engine,
                                         final EngineField[][] params) {
    return new FundAccount(_address,
                           discriminator,
                           name, name.getBytes(UTF_8),
                           uri, uri.getBytes(UTF_8),
                           treasury,
                           shareClasses,
                           openfunds,
                           openfundsUri, openfundsUri.getBytes(UTF_8),
                           manager,
                           engine,
                           params);
  }

  public static FundAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FundAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FundAccount> FACTORY = FundAccount::read;

  public static FundAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var uri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var treasury = readPubKey(_data, i);
    i += 32;
    final var shareClasses = Borsh.readPublicKeyVector(_data, i);
    i += Borsh.len(shareClasses);
    final var openfunds = readPubKey(_data, i);
    i += 32;
    final var openfundsUri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var manager = readPubKey(_data, i);
    i += 32;
    final var engine = readPubKey(_data, i);
    i += 32;
    final var params = Borsh.readMultiDimensionVector(EngineField.class, EngineField::read, _data, i);
    return new FundAccount(_address,
                           discriminator,
                           name, name.getBytes(UTF_8),
                           uri, uri.getBytes(UTF_8),
                           treasury,
                           shareClasses,
                           openfunds,
                           openfundsUri, openfundsUri.getBytes(UTF_8),
                           manager,
                           engine,
                           params);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.write(_name, _data, i);
    i += Borsh.write(_uri, _data, i);
    treasury.write(_data, i);
    i += 32;
    i += Borsh.write(shareClasses, _data, i);
    openfunds.write(_data, i);
    i += 32;
    i += Borsh.write(_openfundsUri, _data, i);
    manager.write(_data, i);
    i += 32;
    engine.write(_data, i);
    i += 32;
    i += Borsh.write(params, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.len(_name)
         + Borsh.len(_uri)
         + 32
         + Borsh.len(shareClasses)
         + 32
         + Borsh.len(_openfundsUri)
         + 32
         + 32
         + Borsh.len(params);
  }
}
